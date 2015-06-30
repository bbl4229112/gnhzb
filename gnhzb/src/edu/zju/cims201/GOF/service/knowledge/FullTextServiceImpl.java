package edu.zju.cims201.GOF.service.knowledge;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.util.Streams;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.BooleanClause;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

import org.apache.lucene.search.highlight.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.textmining.text.extraction.WordExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;
import org.wltea.analyzer.lucene.IKSimilarity;

import edu.zju.cims201.GOF.dao.knowledge.HotwordDao;
import edu.zju.cims201.GOF.dao.knowledge.KnowledgeDao;

import edu.zju.cims201.GOF.hibernate.pojo.HotWord;

import edu.zju.cims201.GOF.hibernate.pojo.Knowledgetype;
import edu.zju.cims201.GOF.hibernate.pojo.Ktype;
import edu.zju.cims201.GOF.hibernate.pojo.KtypeProperty;
import edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge;
import edu.zju.cims201.GOF.hibernate.pojo.Property;
import edu.zju.cims201.GOF.hibernate.pojo.SystemUser;

import edu.zju.cims201.GOF.rs.dto.KnowledgeDTO;
import edu.zju.cims201.GOF.rs.dto.ObjectDTO;
import edu.zju.cims201.GOF.rs.dto.PageDTO;
import edu.zju.cims201.GOF.service.ServiceException;

import edu.zju.cims201.GOF.service.interestmodel.InterestModelService;
import edu.zju.cims201.GOF.service.knowledge.ktype.KtypeService;
import edu.zju.cims201.GOF.service.systemUser.UserService;
import edu.zju.cims201.GOF.util.Constants;
import edu.zju.cims201.GOF.util.JSONUtil;

/**
 * 提供关于知识全文索引和检索的服务接口的实现
 * 
 * @author hebi
 */

@Transactional(readOnly = true)
@Service
public class FullTextServiceImpl implements FullTextService

{
	private static Logger logger = LoggerFactory
			.getLogger(FullTextServiceImpl.class);

	@Resource(name = "knowledgeDao")
	private KnowledgeDao knowledgedao;
	@Resource(name = "hotwordDao")
	private HotwordDao hotworddao;
	@Resource(name = "userServiceImpl")
	private UserService userservice;
	@Resource(name = "interestModelServiceImpl")
	private InterestModelService imservice;
	

	public int addHotWord(HotWord hotword) {
		hotworddao.save(hotword);
		hotworddao.flush();
		return 1;
	}

	@SuppressWarnings("unchecked")
	public void indexAllKnowledge() {
		// 首先删除原来的index文件
		Directory directory = null;
		IndexWriter iwriter = null;
		// IndexSearcher isearcher = null;
		String indexdir = Constants.INDEXPATH;

		try {
			directory = FSDirectory.getDirectory(indexdir);

			String[] allfiles = directory.listAll();

			for (String file : allfiles) {
				file = file;

				moveTemp2Target(Constants.INDEXPATH,
						Constants.INDEXPATH_BACKUP, file);

				// directory.deleteFile(file);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "0");
		List<MetaKnowledge> knowledgelist = knowledgedao.createQuery(
				"from MetaKnowledge o where o.status != :status and domainnode is not NULL", params)
				.list();
	//	System.out.println();
		indexListKnowledge(knowledgelist);

	}

	private void moveTemp2Target(String tempdir, String targetdir,
			String filename) {
		try {
			File savedira = new File(targetdir);
			if (!savedira.exists())
				savedira.mkdirs();// 如果目录不存在就创建

			File tempFilea = new File(tempdir + "\\" + filename);
			if (tempFilea.exists()) {

				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyyMMdd-HHmmss");
				String timestamp = format.format(date);

				BufferedInputStream ina;
				ina = new BufferedInputStream(new FileInputStream(tempFilea));
				BufferedOutputStream outa = new BufferedOutputStream(
						new FileOutputStream(new File(targetdir, timestamp
								+ filename)));

				Streams.copy(ina, outa, true);
				tempFilea.delete();// 删除临时文件
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("文件移动错误");
		}

	}

	public void indexListKnowledge(List<MetaKnowledge> knowledgelist) {
		System.out.println("共有文章：" + knowledgelist.size());
		for (MetaKnowledge mg : knowledgelist) {

			try {
				indexKnowledge(mg);
			} catch (CorruptIndexException e) {
				e.printStackTrace();
				throw new ServiceException("构建索引崩溃");
			} catch (IOException e) {
				e.printStackTrace();
				throw new ServiceException("读写异常");
			}

		}

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void indexKnowledge(MetaKnowledge metaknowledge)
			throws CorruptIndexException, IOException {

		Analyzer analyzer = new IKAnalyzer();
		Directory directory = null;
		IndexWriter iwriter = null;
		// IndexSearcher isearcher = null;
		String indexdir = Constants.INDEXPATH;

		directory = FSDirectory.getDirectory(indexdir);
		try {
			iwriter = new IndexWriter(directory, analyzer, false);
		} catch (FileNotFoundException e) {
			iwriter = new IndexWriter(directory, analyzer, true);

		}
		try {
			// int t = 0;

			// 获取文章全文
			String content = "";
			// TODO 判断全文是否是word文档
			File file = new File(Constants.SOURCEFILE_PATH + "\\"
					+ metaknowledge.getKnowledgesourcefilepath());

			if (file.canRead()) {
				if (file != null) {

					try {
						FileInputStream in;
						System.out.println("当前文档是："
								+ metaknowledge.getTitlename());
						in = new FileInputStream(Constants.SOURCEFILE_PATH
								+ "\\"
								+ metaknowledge.getKnowledgesourcefilepath());

						WordExtractor extractor = new WordExtractor(in);

						content = extractor.getText();

						in.close();
						// t++;
					} catch (FileNotFoundException e) {

						System.out.println("FileNotFoundException");
						e.printStackTrace();
						logger.error("知识源文件没有找到！");
					} catch (Exception e) {
						logger.warn("读取知识源文件错误");
						e.printStackTrace();
					}

				}
			}

			KnowledgeDTO mg = new KnowledgeDTO(metaknowledge);
			Document doc = new Document();
			doc.add(new Field("id", mg.getId() + "", Field.Store.YES,
					Field.Index.ANALYZED));
			System.out.println("id是：" + mg.getId());
			System.out.println("标题是：" + mg.getTitleName());
			String title = mg.getTitleName();
			if (null == title)
				title = "";
			doc.add(new Field("titlename", title, Field.Store.YES,
					Field.Index.ANALYZED));
			System.out.println("作者是：" + JSONUtil.write(mg.getKAuthors()));

			String kauthor = JSONUtil.write(mg.getKAuthors());
			if (null == kauthor)
				kauthor = "";
			doc.add(new Field("kauthors", kauthor, Field.Store.YES,
					Field.Index.NO));
			System.out.println("关键词是：" + JSONUtil.write(mg.getKeywords()));
			String keywords = JSONUtil.write(mg.getKeywords());
			if (null == keywords)
				keywords = "";
			doc.add(new Field("keywords", keywords, Field.Store.YES,
					Field.Index.NO));
			System.out.println("知识上传时间是：" + mg.getUploaddate());

			doc.add(new Field("uploadtime", mg.getUploaddate(),
					Field.Store.YES, Field.Index.NO));
			System.out.println("知识模板是：" + mg.getKtype().getName());
			doc.add(new Field("ktypename", mg.getKtype().getName(),
					Field.Store.YES, Field.Index.ANALYZED));

			doc.add(new Field("ktype", JSONUtil.write(mg.getKtype()),
					Field.Store.YES, Field.Index.NO));
			doc.add(new Field("knowledgetype", JSONUtil.write(mg
					.getKnowledgetype()), Field.Store.YES, Field.Index.NO));
			System.out.println("知识类型是：" + mg.getKnowledgetype().getName());
			doc.add(new Field("knowledgetypename", mg.getKnowledgetype()
					.getName(), Field.Store.YES, Field.Index.ANALYZED));
			System.out.println("知识类型id是：" + mg.getKnowledgetype().getId());
			doc.add(new Field("knowledgetypeid", mg.getKnowledgetype().getId()
					+ "", Field.Store.YES, Field.Index.ANALYZED));
			// System.out.println("知识类型是：" + mg.getKtype().getName());
			// doc.add(new Field("ktypeename", mg.getKtype().getName() + "",
			// Field.Store.YES, Field.Index.ANALYZED));
			System.out.println("密级是：" + mg.getSecurityLevel());
			String securitylevel = mg.getSecurityLevel();
			if (null == securitylevel)
				securitylevel = "公开";
			doc.add(new Field("securitylevel", securitylevel, Field.Store.YES,
					Field.Index.NO));
			System.out.println("知识等级是：" + mg.getStatus());
			doc.add(new Field("status", mg.getStatus(), Field.Store.YES,
					Field.Index.NO));

			System.out.println("摘要是：" + mg.getAbstractText());
			String abstractText = mg.getAbstractText();
			if (null == abstractText)
				abstractText = "";
			doc.add(new Field("abstracttext", abstractText, Field.Store.YES,
					Field.Index.ANALYZED));
			System.out.println("知识所属域：" + JSONUtil.write(mg.getDomainNode()));
			doc.add(new Field("domainnode", JSONUtil.write(mg.getDomainNode()),
					Field.Store.YES, Field.Index.NO));
			System.out.println("知识的上传者是：" + JSONUtil.write(mg.getUploader()));
			doc.add(new Field("uploader", JSONUtil.write(mg.getUploader()),
					Field.Store.YES, Field.Index.NO));
			// 如果没有正文或者正文格式不能抽取的情况 以标题和摘要 关键词为全文内容
			if (null == content || content.equals("")) {
			
				content = "[标题]" + mg.getTitleName();
				if (null != mg.getAbstractText())
					content += "[摘要]" + mg.getAbstractText();
				if (null != mg.getKeywords()) {
					content += "[关键词]";
					for (ObjectDTO keyword : mg.getKeywords()) {
						content += keyword.getName();
					}

				}
				System.out.println(metaknowledge.getClass().toString());
				if(!metaknowledge.getClass().toString().equals("class edu.zju.cims201.GOF.hibernate.pojo.MetaKnowledge"))
			//	metaknowledge=knowledgeservice.getMetaknowledge(metaknowledge.getId())
				for(KtypeProperty kp:metaknowledge.getKtype().getKtypeproperties())
				{	Property p=kp.getProperty();
					if(p.getIsCommon().equals(false)) {
					
						//System.out.println("+++++++++++"+PropertyUtils.getProperty(k, kp.getName()));
						Object tempvalue="";
						try {
							if(null!=PropertyUtils.getProperty(metaknowledge, p.getName())){
							if(p.getPropertyType().equals("java.util.Date"))
								{
								      
										tempvalue=((Date)PropertyUtils.getProperty(metaknowledge, p.getName())).toLocaleString();
								
								}
							else{
							if(p.getName().equals("knowledgetype"))
								
							{
								tempvalue=((Knowledgetype)PropertyUtils.getProperty(metaknowledge, p.getName())).getKnowledgeTypeName();
							}
							else
								
								tempvalue=PropertyUtils.getProperty(metaknowledge, p.getName()).toString();
							
							}
							content+="["+kp.getShowname()+"]"+tempvalue;

							}
							} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
//						kp.setValue(tempvalue);
//						expandkps.add(kp);			
					}
				}
				// " [摘要]"+mg.getAbstractText()+" [关键词] "+JSONUtil.write(mg.getKeywords());
				System.out.println(content);

			}
			content = content.replaceAll(" FORMTEXT ", "");
			content = content.replaceAll("<", "");
			content = content.replaceAll(">", "");
			content = content.replaceAll("", "");
			content = content.replaceAll("", "");
			content = content.replaceAll(" ", "");
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			// System.out.println("全文是："+content);
			doc.add(new Field("content", content, Field.Store.YES,
					Field.Index.ANALYZED));
			iwriter.addDocument(doc);
			// 打印被索引的全文的分词结果，已经被注释

			iwriter.optimize();
			iwriter.close();
			System.out.println("文章已被索引");
			imservice.saveMessageAndSubscribeInfo(metaknowledge);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
			iwriter.close();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
			iwriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			iwriter.close();

		}

	}

	@SuppressWarnings("unchecked")
	public List<String> listHotWords() {
		List<String> hotwordlist = hotworddao
				.getSession()
				.createQuery(
						"Select o.wordName from HotWord o  group by o.wordName order by count(o.wordName) desc")
				.setMaxResults(Constants.HOTWORD_LIMIT).list();
		return hotwordlist;
	}

	@SuppressWarnings("unchecked")
	public List<String> searchHotWords(String keyword) {
		System.out.println(keyword);
		List<String> hotwordlist = hotworddao
				.getSession()
				.createQuery(
						"Select o.wordName from HotWord o where o.wordName like '%"
								+ keyword
								+ "%' group by o.wordName order by count(o.wordName) desc")
				.setMaxResults(Constants.HOTWORD_LIMIT).list();
		return hotwordlist;

	}

	public PageDTO listRecommendedKnowledges(MetaKnowledge knowledge,
			PageDTO page, String inktype) {

		if (null != inktype && inktype.equals("inktype"))
			return searchKnowledge(knowledge.getTitlename(), page, knowledge
					.getKtype().getName());
		else
			return searchKnowledge(knowledge.getTitlename(), page, null);
	}

	@SuppressWarnings("deprecation")
	public PageDTO searchKnowledge(String keyword, PageDTO page,
			String ktypename) {
		if (null == keyword || keyword.trim().equals(""))
			throw new ServiceException("请不要检索空数据");

//		SystemUser user = userservice.getUser();

		if (null == page) {
			page = new PageDTO();
			page.setPagesize(Constants.rawPrepage);
			page.setFirstindex(0);
		}
		Directory directory = null;

		IndexSearcher isearcher = null;
		String indexdir = Constants.INDEXPATH;
		try {
			directory = FSDirectory.getDirectory(indexdir);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ServiceException("查询索文件io错误");
		}
		try {

			// 实例化搜索器
			isearcher = new IndexSearcher(directory);
			// ////// 在索引器中使用 IKSimilarity IKSimilarity IKSimilarity
			// IKSimilarity 相似度评估器
			// 构造查询条件
			BooleanClause.Occur ktypeoccour = BooleanClause.Occur.MUST_NOT;

			if (null != ktypename && !ktypename.equals("")) {
				ktypeoccour = BooleanClause.Occur.MUST;
			} else
				ktypename = "";

			String[] keywords = keyword.split(" ");
			String[] fields = new String[2 + keywords.length * 2];
			// 知识等级必须大于等于1
			fields[0] = "status";
			// 知识类型的限制
			fields[1] = "ktypename";
			// 关键词的限制

			String[] queries = new String[2 + keywords.length * 2];
			queries[0] = "未入库";
			queries[1] = ktypename;

			BooleanClause.Occur[] flags = new BooleanClause.Occur[2 + keywords.length * 2];
			flags[0] = BooleanClause.Occur.MUST_NOT;
			flags[1] = ktypeoccour;

			for (int k = 0; k < keywords.length; k++) {
				fields[2 + k] = "content";
				queries[2 + k] = keywords[k];
				// System.out.println(keywords[k]);
				flags[2 + k] = BooleanClause.Occur.MUST;
				// 存储用户的查询记录用于hotkeyword

			}
			for (int k = 0; k < keywords.length; k++) {
				fields[2 + keywords.length + k] = "titlename";
				queries[2 + keywords.length + k] = keywords[k];
				// System.out.println(keywords[k]);
				flags[2 + keywords.length + k] = BooleanClause.Occur.SHOULD;

			}

			Query oquery = IKQueryParser
					.parseMultiField(fields, queries, flags);

			isearcher.setSimilarity(new IKSimilarity());
			Hits hits = isearcher.search(oquery);

			// System.out.println(" 命中： " + hits.length());

			List<KnowledgeDTO> resultlist = new ArrayList<KnowledgeDTO>();
			int totlalnum = 0;

			totlalnum = hits.length();
			if (totlalnum > 0) {
				TopDocs topDocs = isearcher.search(oquery, hits.length());

				// 输出结果
				ScoreDoc[] scoreDocs = topDocs.scoreDocs;
				int index = page.getFirstindex()
						* Integer.parseInt(String.valueOf(page.getPagesize()));

				int pagemax = index
						+ Integer.parseInt(String.valueOf(page.getPagesize()));
				if (pagemax > totlalnum)
					pagemax = totlalnum;
				for (int i = index; i < pagemax; i++) {
					Document targetDoc = isearcher.doc(scoreDocs[i].doc);
					// System.out.println(" 分数： " + scoreDocs[i].score);
					KnowledgeDTO mg = new KnowledgeDTO();
					mg.setAbstractText(targetDoc.getField("abstracttext")
							.stringValue());

					String titlename = targetDoc.getField("titlename")
							.stringValue();
					mg.setTitleName(titlename);
					String titleshowname = getHighLightString("titlename",
							titlename, oquery);

					if (null == titleshowname)
						titleshowname = titlename;
					mg.setTitleShowName(titleshowname);

					// System.out.println(titleshowname);
					mg.setId(Long.valueOf(targetDoc.getField("id")
							.stringValue()));
					mg.setUploadTime(targetDoc.getField("uploadtime")
							.stringValue());
					mg.setKtype(getObjectdto(targetDoc.getField("ktype")
							.stringValue()));
					if (null != targetDoc.getField("knowledgetype")) {
						mg.setKnowledgetype(getObjectdto(targetDoc.getField(
								"knowledgetype").stringValue()));
					}
					mg.setKAuthors(getObjectdtolist(targetDoc.getField(
							"kauthors").stringValue()));
					mg.setKeywords(getObjectdtolist(targetDoc.getField(
							"keywords").stringValue()));
					String content = targetDoc.getField("content")
							.stringValue();
					mg.setContent(getHighLightString("content", content, oquery));

					resultlist.add(mg);

				}
				page.setTotal(totlalnum);
				page.setData(resultlist);
				System.out.println("检索到查询结果");
			} else {
				System.out.println("没有检索到查询结果");
				page.setTotal(0);
				page.setData(null);

			}

		} catch (Exception e) {
			// System.out.println("抛出了异常，没有在这里返回跳转1");
			e.printStackTrace();
			throw new ServiceException("查询索引文件错误");

		} finally {
			if (isearcher != null) {
				try {
					isearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
					// System.out.println("抛出了异常，没有在这里返回跳转2");
					throw new ServiceException("查询文件isearcher io错误");
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new ServiceException("查询索引文件directory io错误");
				}
			}
		}

		return page;

	}

	private String getHighLightString(String field, String content, Query oquery)
			throws IOException, InvalidTokenOffsetsException {

		QueryScorer scorer = new QueryScorer(oquery);
		SimpleHTMLFormatter fromatter = new SimpleHTMLFormatter(
				"<span class=\"knowledge_list_highlight\">", "</span>");
		Highlighter highlighter = new Highlighter(fromatter, scorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(380));
		TokenStream tokenStream = new IKAnalyzer().tokenStream(field,
				new StringReader(content));
		content = highlighter.getBestFragment(tokenStream, content);

		if (null != content) {
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			content = content.replaceAll("", " ");
			// System.out.println("content="+content);
		}
		return content;

	}

	@SuppressWarnings("unchecked")
	private ObjectDTO getObjectdto(String json) {
		ObjectDTO odto = new ObjectDTO();
		HashMap result = (HashMap) JSONUtil.read(json);
		odto.setId(new Long(result.get("id").toString()));
		odto.setName(result.get("name").toString());
		return odto;
	}

	@SuppressWarnings("unchecked")
	private List<ObjectDTO> getObjectdtolist(String json) {
		List<ObjectDTO> odtolist = new ArrayList();
		List<HashMap> results = (List<HashMap>) JSONUtil.read(json);
		for (HashMap result : results) {
			ObjectDTO odto = new ObjectDTO();
			odto.setId(new Long(result.get("id").toString()));
			odto.setName(result.get("name").toString());
			odto.setShowname(result.get("name").toString());
			odtolist.add(odto);
		}

		return odtolist;
	}

}
