package prefuse.owl2prefuse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import prefuse.owl2prefuse.graph.GraphDisplay;
import prefuse.owl2prefuse.graph.GraphPanel2;
import prefuse.owl2prefuse.graph.OWLGraphConverter;
import prefuse.owl2prefuse.graph.GraphDisplay2;
import prefuse.owl2prefuse.graph.GraphPanel;

import prefuse.owl2prefuse.graph.OWLReGtreeConverter;

import prefuse.data.Graph;

import prefuse.util.io.IOLib;
import prefuse.util.io.SimpleFileFilter;

/**
 * This class loads a GraphML or a TreeML file and returns a graph or a tree
 * respectively.
 * <p/>
 * Project OWL2Prefuse <br/>
 * Demo.java created 2 januari 2007, 10:40
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 *
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision: 1.3 $$, $$Date: 2009/10/08 16:43:11 $$
 */
public class Demo2 implements ActionListener
{
    /**
     * The path to the OWL file.
     */
    private static final String OWL_FILE = "prefuse/owl2prefuse/test1000.owl";
    
    /**
     * The path to the GraphML file.
     */
    private static final String GRAPHML_FILE = "prefuse/owl2prefuse/graphml-sample.xml";
    
    /**
     * The path to the TreeML file.
     */
    private static final String TREEML_FILE = "prefuse/owl2prefuse/treeml-sample.xml";
    
    /**
     * The JFrame of the demo application.
     */
    private JFrame m_frame;
    
    /**
     * The tabbed pane of the demo application.
     */
    private JTabbedPane  m_tabbedPane;
    
    /**
     * The file chooser, used by this demo.
     */
    private JFileChooser m_fc;
    
    /**
     * The Prefuse graph.
     */
    private Graph m_graph;
    private Graph rm_graph;
  //  public static int i=0;
    /**
     * The graph panel.
     */
    private GraphPanel m_graphPanel;
    private GraphPanel rm_graphPanel;
    /**
     * The Prefuse tree.
     */
    private Graph m_gtree;
    private Graph rm_gtree;
    /**
     * The tree panel.
     */
    private GraphPanel2 m_gtreePanel;
    private GraphPanel2 m_rgtreePanel;
    /**
     * The action command for opening an ontology.
     */
    private final static String OPEN_ONTOLOGY = "Open ontology";
    
    /**
     * The action command for opening GraphML.
     */
    private final static String OPEN_GRAPHML = "Open GraphML";
    
    /**
     * The action command for opening TreeML.
     */
    private final static String OPEN_TREEML = "Open TreeML";
    
    /**
     * The action command for exiting the demo application.
     */
    private final static String EXIT = "Exit";
    
    /**
     * The action command for exporting to GraphML
     */
    private final static String EXPORT_GRAPHML = "Export GraphML";
    
    /**
     * The action command for exporting the graph to an image.
     */
    private final static String EXPORT_IMAGE_GRAPH = "Graph image";
    
    /**
     * The action command for exporting TreeML.
     */
    private final static String EXPORT_TREEML = "Export TreeML";
    
    /**
     * The action command for exporting the tree to an image.
     */
    private final static String EXPORT_IMAGE_TREE = "Tree image";
    
    /**
     * Indicates if the graph distance filter should be used.
     */
    private final static boolean GRAPH_DISTANCE_FILTER = true;
    
    /**
     * Indicates if a legend should be displayed.
     */
    private final static boolean LEGEND = true;
    
    /**
     * Indicates if the hops control widget for the graph distance filter should 
     * be displayed.
     */
    private final static boolean HOPS_CONTROL_WIDGET = true;
    
    /**
     * Indicates if the orientation control widget for the tree should be displayed.
     */
    private final static boolean ORIENTATION_CONTROL_WIDGET = true;

    /**
     * Creates a new instance of the Demo class.
     */
    public Demo2(String searchname)
    {
    //树图正向
   //获取owl文件 解析成树图形式 这里是正向 	
    	OWLReGtreeConverter retreeConverter = new OWLReGtreeConverter(OWL_FILE,true, searchname,"tree");
    	//得到树类型对象rmgree
    	rm_gtree =retreeConverter.getGraph();
    	//得到display对象rgtreeDisp
    	GraphDisplay2 rgtreeDisp = new GraphDisplay2(rm_gtree, GRAPH_DISTANCE_FILTER,rm_gtree.getFocusindex(),1,"");
    	//得到panel
    	m_rgtreePanel = new GraphPanel2(rgtreeDisp, LEGEND, HOPS_CONTROL_WIDGET);
//    	//树反向图
//    	OWLGraphConverter treeConverter = new OWLGraphConverter(OWL_FILE, true,searchname,"tree");
//    	m_gtree =treeConverter.getGraph();
//        GraphDisplay2 gtreeDisp = new GraphDisplay2(m_gtree, GRAPH_DISTANCE_FILTER,m_gtree.getFocusindex(),1,"backward");
//     m_gtreePanel = new GraphPanel2(gtreeDisp, LEGEND, HOPS_CONTROL_WIDGET);
////网络图反向
//       OWLGraphConverter graphConverter = new OWLGraphConverter(OWL_FILE, true,searchname,"graph");
//       m_graph = graphConverter.getGraph();
//       GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER,m_graph.getFocusindex(),0,"backward");
//      m_graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
//      //网络图正向
//  	OWLReGtreeConverter regraphConverter = new OWLReGtreeConverter(OWL_FILE,true, searchname,"graph");
//    rm_graph = regraphConverter.getGraph();
//    GraphDisplay rgraphDisp = new GraphDisplay(rm_graph, GRAPH_DISTANCE_FILTER,rm_graph.getFocusindex(),0,"");
//    rm_graphPanel = new GraphPanel(rgraphDisp, LEGEND, HOPS_CONTROL_WIDGET);
        // Create the tabbed pane which contains the the home tab, the tree tabs
        // and the graph tabs.
        //添加applet菜单

        m_tabbedPane = new JTabbedPane();
        
        //定义按钮graph以及tree的说明 快捷键Alt+G
        m_tabbedPane.addTab("正向树图", m_rgtreePanel);
        m_tabbedPane.setToolTipTextAt(0, "正向树图..");
        m_tabbedPane.setMnemonicAt(0, KeyEvent.VK_R);
 
//        //定义按钮tree以及tree的说明 快捷键Alt+T
//        m_tabbedPane.addTab("反向树图", m_gtreePanel);
//        m_tabbedPane.setToolTipTextAt(1, "反向树图..");
//        m_tabbedPane.setMnemonicAt(1, KeyEvent.VK_T);
//////        //定义按钮home以及home的说明 快捷键Alt+H
//        m_tabbedPane.addTab("反向网图", m_graphPanel);
//        m_tabbedPane.setToolTipTextAt(2, "反向网图..");
//        m_tabbedPane.setMnemonicAt(2, KeyEvent.VK_H);
//        
//        m_tabbedPane.addTab("正向网图", rm_graphPanel);
//        m_tabbedPane.setToolTipTextAt(3, "正向网图..");
//        m_tabbedPane.setMnemonicAt(3, KeyEvent.VK_H);
        
        // Create the frame which shows the application.
        //为图像展示创建框架
        m_frame = new JFrame("OWL2Prefuse v1.2 | Demo application");
        m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   //     m_frame.setJMenuBar(getMenuBar());
        m_frame.add(m_tabbedPane);
        m_frame.pack();
        m_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        m_frame.setVisible(true);
        
        // Create the file chooser.
       m_fc = new JFileChooser();
    }
    
    /**
     * This methods starts the demo.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
    	 String searchname="运载火箭";
        // Start the demo application.
        Demo2 example = new Demo2(searchname);
    }
    
    /**
     * Create the menu bar which is displayed in the demo.
     * @return The menu bar.
     */
    //应用程序菜单部分内容
//    private JMenuBar getMenuBar()
//    {
//        JMenuBar menuBar;
//        JMenu file, openSubMenu, output;
//        JMenuItem menuItem;
//        
//        // Create the file bar.
//        menuBar = new JMenuBar();
//        
//        // Build the file file.
//        file = new JMenu("File");
//        file.setMnemonic(KeyEvent.VK_F);
//        
//        // The open sub menu.
//        openSubMenu = new JMenu("Open");
//        openSubMenu.setMnemonic(KeyEvent.VK_O);
//        
//        menuItem = new JMenuItem("Open ontology");
//        menuItem.setActionCommand(OPEN_ONTOLOGY);
//        menuItem.addActionListener(this);
//        openSubMenu.add(menuItem);
//        menuItem = new JMenuItem("Open GraphML");
//        menuItem.setActionCommand(OPEN_GRAPHML);
//        menuItem.addActionListener(this);
//        openSubMenu.add(menuItem);
//        menuItem = new JMenuItem("Open TreeML");
//        menuItem.setActionCommand(OPEN_TREEML);
//        menuItem.addActionListener(this);
//        openSubMenu.add(menuItem);
//        
//        file.add(openSubMenu);
//        file.addSeparator();
//        
//        menuItem = new JMenuItem("Exit");
//        menuItem.setActionCommand(EXIT);
//        menuItem.addActionListener(this);
//        menuItem.setMnemonic(KeyEvent.VK_X);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
//        file.add(menuItem);
//        
//        // The output menu.
//        output = new JMenu("Output");
//        output.setMnemonic(KeyEvent.VK_O);
//        
//        // The graph sub menu.
//        JMenu graphSubMenu = new JMenu("Graph");
//        menuItem = new JMenuItem("Export GraphML");
//        menuItem.setActionCommand(EXPORT_GRAPHML);
//        menuItem.addActionListener(this);
//        menuItem.setMnemonic(KeyEvent.VK_G);
//        graphSubMenu.add(menuItem);
//        menuItem = new JMenuItem("Export Image");
//        menuItem.setActionCommand(EXPORT_IMAGE_GRAPH);
//        menuItem.addActionListener(this);
//        menuItem.setMnemonic(KeyEvent.VK_R);
//        graphSubMenu.add(menuItem);
//        output.add(graphSubMenu);
//        
//        // Te tree sub menu.
//        JMenu treeSubMenu = new JMenu("Tree");
//        menuItem = new JMenuItem("Export TreeML");
//        menuItem.setActionCommand(EXPORT_TREEML);
//        menuItem.addActionListener(this);
//        menuItem.setMnemonic(KeyEvent.VK_T);
//        treeSubMenu.add(menuItem);
//        menuItem = new JMenuItem("Export Image");
//        menuItem.setActionCommand(EXPORT_IMAGE_TREE);
//        menuItem.addActionListener(this);
//        menuItem.setMnemonic(KeyEvent.VK_R);
//        treeSubMenu.add(menuItem);
//        output.add(treeSubMenu);
//        
//        // Add the menu's to the menu bar.
//        menuBar.add(file);
//        menuBar.add(output);
//        
//        return menuBar;
//    }
    
    /**
     * This methods is called when an action is performed, by one of the menu bar items.
     * @param e The thrown ActionEvent.
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if (action.equals(OPEN_ONTOLOGY)) openOntology();
        else if (action.equals(OPEN_GRAPHML)) openGraphML();
        else if (action.equals(OPEN_TREEML)) openTreeML();
        else if (action.equals(EXIT)) System.exit(0);
        else if (action.equals(EXPORT_GRAPHML)) exportGraphML();
        else if (action.equals(EXPORT_TREEML)) exportTreeML();
        else if (action.equals(EXPORT_IMAGE_GRAPH)) exportGraphic((ExportableGraphic) m_graphPanel);
        else if (action.equals(EXPORT_IMAGE_TREE)) exportGraphic((ExportableGraphic) m_gtreePanel);
    }
    
    /**
     * This methods opens a filechooser to facilitate the user in opening and
     * saving files.
     * @param p_opener Boolean indicating if this filechooser should be an
     * opener or a saver.
     * @param p_fileFilters An array containing the appropriate file filters.
     * @return The path to the chosen file or null if no file is chosen.
     */
    private String openFileChooser(boolean p_opener, SimpleFileFilter[] p_fileFilters)
    {
        String retval = null;
        
        // Take care of the file filters.
        if (p_fileFilters.length == 0)
        {
            m_fc.setAcceptAllFileFilterUsed(true);
        }
        else
        {
            m_fc.resetChoosableFileFilters();
            m_fc.setAcceptAllFileFilterUsed(false);
            for (int i = 0; i < p_fileFilters.length; i++)
            {
                m_fc.addChoosableFileFilter(p_fileFilters[i]);
            }
        }
        
        // Open a dialog, either an opener of a saver.
        int fcState;
        if (p_opener)
        {
            m_fc.setDialogTitle("Open file");
            fcState = m_fc.showOpenDialog(m_frame);
        }
        else
        {
            m_fc.setDialogTitle("Save file");
            fcState = m_fc.showSaveDialog(m_frame);
        }
        
        if (fcState == JFileChooser.APPROVE_OPTION)
        {
            File file = m_fc.getSelectedFile();
            retval = file.getPath();
        }
        
        return retval;
    }
    
    /**
     * This method is triggered when a user wants to open an ontology. It provides the
     * user with a file choser and handles the opening and visiualizing of the chosen
     * ontology. The opened ontology is visualized both as a simple tree, an advanced
     * tree and a graph.
     */
    private void openOntology()
    {
        // Create the file filter.
        SimpleFileFilter[] filters = new SimpleFileFilter[] {
                    new SimpleFileFilter("owl", "OWL ontologies (*.owl)")
        };
        
        // Open the file.
        String file = openFileChooser(true, filters);
        
        // Process the file.
        if (file != null)
        {
//            OWLTreeConverter treeConverter = new OWLTreeConverter(file,"");
//        
//        //	Object[] result2=treeConverter.getTree();
//        //    int t=Integer.parseInt(result2[1].toString());
//        	m_gtree =treeConverter.getTree();
//            TreeDisplay treeDisp = new TreeDisplay(m_gtree,0);
//            TreePanel treePanel = new TreePanel(treeDisp, LEGEND, ORIENTATION_CONTROL_WIDGET);
//            m_tabbedPane.setComponentAt(1, treePanel);
//            
//            OWLGraphConverter graphConverter = new OWLGraphConverter(file, true,"");
//        //    Object[] result=graphConverter.getGraph();
//            m_graph =graphConverter.getGraph();
//    //        int i=Integer.parseInt(result[1].toString());
//   //         System.out.println("i="+i);
//            GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER,0);
//            GraphPanel graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
//            m_tabbedPane.setComponentAt(2, graphPanel);
       }
    }
    
    /**
     * This method is triggered when a user wants to open a GraphML file. It provides
     * the user with a file choser and handles the opening and visiualizing of the chosen
     * GraphML file. The opened GraphML is loaded after which it is visualized.
     */
    private void openGraphML()
    {
        // Create the file filter.
        SimpleFileFilter[] filters = new SimpleFileFilter[] {
                    new SimpleFileFilter("xml", "Graph ML (*.xml)")
        };
        
        // Open the file.
        String file = openFileChooser(true, filters);
        
        // Process the file.
        if (file != null)
        {
            m_graph = Loader.loadGraphML(file);
            GraphDisplay2 disp = new GraphDisplay2(m_graph, GRAPH_DISTANCE_FILTER,0,0,"");
            GraphPanel2 panel = new GraphPanel2(disp, LEGEND, HOPS_CONTROL_WIDGET);
            m_tabbedPane.setComponentAt(2, panel);
        }
    }
    
    /**
     * This method is triggered when a user wants to open a TreeML file. It provides
     * the user with a file choser and handles the opening and visiualizing of the chosen
     * TreeML file. The opened TreeML is loaded after which it is visualized.
     */
    private void openTreeML()
    {
//        // Create the file filter.
//        SimpleFileFilter[] filters = new SimpleFileFilter[] {
//                    new SimpleFileFilter("xml", "Tree ML (*.xml)")
//        };
//        
//        // Open the file.
//        String file = openFileChooser(true, filters);
//        
//        // Process the file.        
//        if (file != null)
//        {
//            m_gtree = Loader.loadTreeML(file);
//            TreeDisplay disp = new TreeDisplay(m_gtree,0);
//            TreePanel panel = new TreePanel(disp, LEGEND, ORIENTATION_CONTROL_WIDGET);
//            m_tabbedPane.setComponentAt(1, panel);
//        }
    }
    
    /**
     * This method is triggered when the user wants to export the graph to GraphML.
     * It presents the user a file chooser, which can be used to pick the file to
     * which the GraphML is to be saved. After that, the program exports the graph
     * to GraphML.
     */
    private void exportGraphML()
    {
        // Create the file filter.
        SimpleFileFilter[] filters = new SimpleFileFilter[] {
                    new SimpleFileFilter("xml", "Graph ML (*.xml)")
        };
        
        // Save the file.
        String file = openFileChooser(false, filters);
        
        // Write the file.
        if (file != null)
        {
            String extension = file.substring(file.length() - 4);
            if (!extension.equals(".xml")) file = file + ".xml";
            Writer.writeGraphML(m_graph, file);
        }
    }
    
    /**
     * This method is triggered when the user wants to export the tree to TreeML.
     * It presents the user a file chooser, which can be used to pick the file to
     * which the TreeML is to be saved. After that, the program exports the tree
     * to TreeML.
     */
    private void exportTreeML()
    {
        // Create the file filter.
//        SimpleFileFilter[] filters = new SimpleFileFilter[] {
//                    new SimpleFileFilter("xml", "Tree ML (*.xml)")
//        };
//        
//        // Save the file.
//        String file = openFileChooser(false, filters);
//        
//        // Write the file.
//        if (file != null)
//        {
//            String extension = file.substring(file.length() - 4);
//            if (!extension.equals(".xml")) file = file + ".xml";
//            Writer.writeTreeML(m_gtree, file);
//        }
    }
    
    /**
     * This method is triggered when the user wants to export the graph (or tree)
     * to a graphic. It presents the user a menu which can be used to specify the
     * filetype and the options of the graphic. After that, the program exports
     * thee graph (or tree) to a graphic.
     * @param p_exportable The panel that needs to be exported to an image.
     */
    private void exportGraphic(ExportableGraphic p_exportable)
    {
        // Create a few simple file filters.
        SimpleFileFilter[] filters = new SimpleFileFilter[] {
                    new SimpleFileFilter("png", "Portable Network Graphics (*.png)"),
                    new SimpleFileFilter("jpg", "JPEG file (*.jpg)")
        };

        // Save the file.
        String filePath = openFileChooser(false, filters);
        
        // Process the file
        if(filePath != null)
        {
            // Get the file the user selected.
            File selectedFile = new File(filePath);
            
            // Determine the file type.
            String fileType = IOLib.getExtension(selectedFile);
            if (fileType == null)
            {
                fileType = ((SimpleFileFilter) m_fc.getFileFilter()).getExtension();
                selectedFile = new File(selectedFile.toString() + "." + fileType);
            }
            
            // If the file already exists the user has to confirm that it may be 
            // replaced by the new export.
            boolean doExport = true;
            if (selectedFile.exists())
            {
                int response = JOptionPane.showConfirmDialog(m_frame, "The file \"" + selectedFile.getName() + "\" already exists.\nDo you want to replace it?", "Confirm Save", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.NO_OPTION) doExport = false;
            }
            
            // Do the export.
            if (doExport) p_exportable.export(selectedFile, fileType);
        }
    }
    
    /**
     * Creates the text to be displayed in the home tab.
     * @return The text to be displayed in the home tab.
     */
    private JTextPane getHomeText()
    {
        String text = "<h3>中国运载火箭研究院知识网络展示平台</h3>" +
                "<p>欢迎大家访问知识网络平台</p>" +
                "<p>在这里找到您需要的知识</p>" +
                "<p>竭诚为您服务<br>" +
                "JeffDong, HEBI</p>"
                ;
        JTextPane tp = new JTextPane();
        tp.setContentType("text/html");
        tp.setText(text);
        
        return tp;
    }

}