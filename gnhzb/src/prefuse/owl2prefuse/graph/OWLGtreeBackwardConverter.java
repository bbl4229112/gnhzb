package prefuse.owl2prefuse.graph;

import java.util.Hashtable;
import java.util.Iterator;

import edu.zju.cims201.GOF.util.Constants;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tuple;
import prefuse.data.tuple.TupleSet;
import prefuse.owl2prefuse.Converter;

public class OWLGtreeBackwardConverter {

	private Graph m_graph;
	private String type;
	private Converter converter;

	Hashtable<String, Node> existsubnodes = new Hashtable<String, Node>();
	Hashtable<String, Node> existrelationnodes = new Hashtable<String, Node>();
	public OWLGtreeBackwardConverter(String owlPath, String searchname,String viewtype) {
		this.type=viewtype;
		converter = ConverterFactory.getInstance().getConverter(owlPath);
		init(searchname);

	}

	private void init(String searchname) {
	OWLGraphtreeConverter graphConverter = (OWLGraphtreeConverter) converter;
	//	OWLReGtreeConverter graphConverter = (OWLReGtreeConverter) converter;
		m_graph = new Graph(true);
		m_graph.getNodeTable().addColumn("URI", String.class);
		m_graph.getNodeTable().addColumn("name", String.class);
		m_graph.getNodeTable().addColumn("label", String.class);
		m_graph.getNodeTable().addColumn("image", String.class);
		m_graph.getNodeTable().addColumn("type", String.class);
		m_graph.getEdgeTable().addColumn("label", String.class);

		Hashtable<String, Node> nodes = graphConverter.getM_nodes();
		Node node = nodes.get("http://www.owl-ontologies.com/unnamed.owl#"+ searchname);
		if (null != node)
			creatroot(node);
	
	}
//创建子树的根节点 rootnode 是从大树上找到的子树的原始根节点，需要重新创建赋值，本方法就是完成这个工作
	private void creatroot(Node rootNode) {
		//创建新树根节点
		Node newNode = m_graph.addNode();
		//对新树根节点赋值，将原始根节点的属性值赋过来
		
		wrapNode(rootNode, newNode,"root");
		existsubnodes.put(newNode.getString("URI"), newNode);
		//从次根节点开始创佳整棵树，creatgraph是一个自迭代方法
		//rootNode是大树原始节点，newNode是当时对应的新树的节点
		creatgraph(rootNode,newNode,0);

	};
//依次遍历构建子节点	
//rootNode是大树原始节点，newNode是当时对应的新树的节点	
	private void creatgraph(Node oldNode, Node newNode,int depth) {
		depth=depth+1;
		if(depth<Constants.ONTO_DEPTH){
//从总树选择的节点 找其对应的指入（inEdges）边		
		Iterator outEdgesIT = oldNode.inEdges();
//如果指入（inEdges）边不为空则遍历找到其target节点
		if(null!=outEdgesIT)
		while (outEdgesIT.hasNext()) {
			Edge edge = (Edge) outEdgesIT.next();
			Node subnode = edge.getSourceNode();
			//target子节点不为空
			if (null != subnode) {
		    //target子节点在新建的树上还没有添加
				
				if (!existsubnodes.containsKey(subnode.getString("URI"))) {
					//判断是否是关系节点
					if (subnode.getString("type") == null)
					{//如果是关系节点 要修改该节点的uri 和节点的label
						if (!existsubnodes.containsKey("指向"+newNode.getString("name")+"的"+subnode.getString("name")))
						{
						//如果不存在该关系节点，则创建该关系节点
							Node newsubNode = m_graph.addNode();
							//对新添加节点拷贝大树对应节点属性值
							wrapNode(subnode, newsubNode,"");	
							m_graph.addEdge(newsubNode, newNode);
							existsubnodes.put("指向"+newNode.getString("name")+"的"+subnode.getString("name"), newsubNode);	
							creatgraph(subnode,newsubNode,depth);
						}
						else
						{
				    //如果已经存在该关系节点,则修改lable后直接添加		
							Node newsubNode=existsubnodes.get("指向"+newNode.getString("name")+"的"+subnode.getString("name"));
							creatgraph(subnode,newsubNode,depth);
						}
				}
					else{
				//如果不是关系节点 则按照术语节点操作	
				//在新树上添加对应节点	
				Node newsubNode = m_graph.addNode();
				//对新添加节点拷贝大树对应节点属性值
				wrapNode(subnode, newsubNode,"");
			    //对新树添加边，此时该新建节点添加到新树上
				m_graph.addEdge(newsubNode, newNode);			
			    //将最新添加到节点记录在被添加节hash表中，用于之后的判断
				

			
					existsubnodes.put(subnode.getString("URI"), newsubNode);	
			    //继续构建下面的分支
					creatgraph(subnode,newsubNode,depth);
					}
				}
				//target子节点在新建的树上还已经添加，处理重复问题，这里需要区别术语节点和关系节点
				else
				{
					   if(null!=type&&type.equals("graph"))
						{
						   Node newsubNode =existsubnodes.get(subnode.getString("URI"));	
						   creatgraph(subnode,newsubNode,depth);
						}
						else{
                 //得到当前新树上的target节点
					 Node newsubNode =existsubnodes.get(subnode.getString("URI"));
						//以nothavesub表示当前的source节点是否已经有该节点
						boolean nothavesub=true;
						//判断nothavesub
						Iterator suboutEdgesIT = newNode.inEdges();
						if(null!=suboutEdgesIT)
							while (suboutEdgesIT.hasNext()) {
								Edge subedge = (Edge) suboutEdgesIT.next();
								Node subsubnode = subedge.getSourceNode();
								//target子节点不为空
								if (null != subsubnode) {
								if(subnode.getString("name").equals(subsubnode.getString("name")))
										{
									nothavesub=false;
									break;
							
										}
								}
						
							}
						//如果当前的source节点没有该节点，则创建一个终止节点
						if(nothavesub)						
						{
						Node newdupsubNode = m_graph.addNode();
						wrapNode(subnode, newdupsubNode,"end");
						m_graph.addEdge(newdupsubNode,newNode );
						}
						
				}
				
				}
			}

		}
		}
	}

	private void wrapNode(Node oldNode, Node newNode,String type) {
         
		newNode.setString("URI", oldNode.getString("URI"));
		newNode.setString("name", oldNode.getString("name"));
	    newNode.setString("label", oldNode.getString("label"));
		newNode.setString("type", oldNode.getString("type"));
	    if(type.equals("root"))
		newNode.setString("image", Constants.ONTO_IMG_FILE_PATH+"/root.gif");
	    else if(type.equals("end")){
	    newNode.setString("image", Constants.ONTO_IMG_FILE_PATH+"/3.gif");
	    newNode.setString("name", oldNode.getString("name")+"*");
	    newNode.setString("type", "class");
	    }
	    else
	    newNode.setString("image", oldNode.getString("image"));	
	}

	/**
	 * Return the created Prefuse graph.
	 * 
	 * @return The created Prefuse graph.
	 */
	public Graph getGraph() {
		return m_graph;
	}
	public static void main(String[] args)
	{
		OWLGtreeBackwardConverter cvt=new OWLGtreeBackwardConverter("prefuse/owl2prefuse/test1000.owl", "运载火箭","tree");
		cvt.getGraph();
	}
}
