import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.sql.*;
import javax.servlet.ServletConfig;
import java.sql.Connection;
import javax.servlet.http.HttpSession;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author dashk
 */
public class clientview extends HttpServlet {
    
    public DbPool ConnPool;
    public Connection con;
    
    
    public void open (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Опыты по машинному обучению</title>"); 
            
            out.println("<style>");
            out.println(".center {width:700px;padding:10px; margin:auto;}");
            out.println("table{font-family: \"Lucida Sans Unicode\", \"Lucida Grande\", Sans-Serif;font-size:13px;background: white;max-width:50%;width:960px;border-collapse:collapse;text-align:left;}");
            out.println("th {font-weight:normal;color: #039;border-bottom: 1px solid #6678b1;padding:8px 6px;}");
            out.println("td {border-bottom: 1px solid #ccc;color: #669;padding: 6px 3px;transition: .3s linear;}");
            out.println("tr:hover td {color: #6699ff;");
            out.println("</style>");
            
            out.println("</head>");
            out.println("<body>");
        }
         catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void end (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("</body>");
            out.println("</html>");
        }
         catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//-------Начальная страница со списком примеров и кнопкой добавления нового
    public void startForm (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
 //           PrintWriter out = response.getWriter();
            out.println("<div class=\"center\">");
            out.println("<form name = \"startForm\" action= \"clientview?act=addexampleform\" method=\"post\">");
            
            out.println("<h3> Примеры: </h3> ");
            out.println("<input type = \"submit\"  value = \"Добавить новый\" id =\"submit\" />");
            //обращение к БД
            Connection con = ConnPool.getConnection();
            String sOp = "select id, nmdata, description from  pr.examples";
        
            Statement op = con.createStatement();

            ResultSet resultSet = op.executeQuery(sOp);
            //вывод из БД названий и описаний с ссылками
             out.println("<div class=\"table\">");
            out.println("<table wight=\"70%\">");
            out.println("<tr><th>№</th><th>Название</th><th>Краткое описание</th></tr>");
            out.println("<th>");
          //  for (int i=0; i<c; i++) {
                while (resultSet.next()){
            //    out.println("<p> <a href = \" http://localhost:8084/servInterface/clientview?act=cardexample&id= "+ c +"\">");
                    int id = resultSet.getInt(1);
                    String nm = resultSet.getString(2);
                    String desc = resultSet.getString(3);
               //     out.println("<tr><td>");
                    out.println("<tr>");
                    out.println("<td><a href=\"clientview?act=cardexample&id=" + id + "\">"+ id + "</td><td></a><a href=\"clientview?act=cardexample&id=" + id + "\">" + nm  + "</td><a href=\"clientview?act=cardexample&id=" + id + "\"><td></a>" + desc +"</td></a></tr>");
                //  out.println("</td></tr>");
                }
                out.println("</table>");
                out.println("</div>");
            
            ConnPool.putConnection(con);
            
            out.println("</form>");
            out.println("</div>");
        }
         catch ( SQLException e ) {
        out.println("<p> не выполнено" + e.getMessage() + "<p>") ;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }
    }
    
//---------Страница с вводом названия и описания примера
    public void newExample (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
         
            out.println("<form name = \"newExample\" action= \"clientview?act=addexample\" method=\"post\">");
            out.println("<h1> Ввод названия и описания примера </h1>");
            out.println("<p> Наименование ");
            
           
            out.println(" <input type = \"text\" name = \"nmData\"> </p>");
            
            out.println("<p> Краткое описание: ");
            out.println(" <input type = \"text\" name = \"descData\" /> </p>");
          
            out.println("<input type = \"submit\" value = \"Сохранить\" id =\"submit\" />");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\">");
                       
            out.println("</form>");        
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void UploadFile (HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
    		//проверяем является ли полученный запрос multipart/form-data
    		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    		if (!isMultipart) {
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    			return;
    		}
                
                System.out.println("--- 1 ---");
     
    		// Создаём класс фабрику 
    		DiskFileItemFactory factory = new DiskFileItemFactory();
     
    		// Максимальный буфера данных в байтах,
    		// при его привышении данные начнут записываться на диск во временную директорию
    		// устанавливаем один мегабайт
    		factory.setSizeThreshold(1024*1024);
    		
    		// устанавливаем временную директорию
    		File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
    		factory.setRepository(tempDir);

                System.out.println("--- 2 ---");
                
    		//Создаём сам загрузчик
    		ServletFileUpload upload = new ServletFileUpload(factory);
    		
    		//максимальный размер данных который разрешено загружать в байтах
    		//по умолчанию -1, без ограничений. Устанавливаем 10 мегабайт. 
    		upload.setSizeMax(1024 * 1024 * 10);

                System.out.println("--- 3 ---");
                
    		try {
    			List items = upload.parseRequest(request);
    			Iterator iter = items.iterator();
    			FileItem item = null;
    			while (iter.hasNext()) {
                System.out.println("--- !!! ---");
                            
    			     item = (FileItem) iter.next();
                                 item.getName();
    			    if (item.isFormField()) {
    			    	//если принимаемая часть данных является полем формы			    	
    			        processFormField(item);
    			    } else {
    			    	//в противном случае рассматриваем как файл
    			        processUploadedFile(item);
    			    }
    			}
                //        String fn = null;
                         session.setAttribute("fn", item.getName());
                        outFile ( request,  response,  item.getName());
    		} catch (Exception e) {
    			e.printStackTrace();
    			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
    		}		
    	}
    	
    	/**
    	 * Сохраняет файл на сервере, в папке upload.
    	 * Сама папка должна быть уже создана. 
    	 * 
    	 * @param item
    	 * @throws Exception
    	 */
    	private void processUploadedFile(FileItem item) throws Exception {
    		File uploadetFile = null;
    		//выбираем файлу имя пока не найдём свободное
    		do{
                    System.out.println( "item name = " + item.getName());
//    			String path = getServletContext().getRealPath("upload/"+random.nextInt() + item.getName());
    			String path = getServletContext().getRealPath("/upload/" + item.getName());
                        System.out.println ( "path = " + path );
    			uploadetFile = new File(path);		
    		}
                while(uploadetFile.exists());
    		
    		//создаём файл
                
                System.out.println("-- создаем файл ---");
    		uploadetFile.createNewFile();
    		//записываем в него данные
                System.out.println("-- записываем в него данные ---");
                                item.write(uploadetFile);
                System.out.println("-- Записано... ---");
    	}
     
    	/**
    	 * Выводит на консоль имя параметра и значение
    	 * @param item
    	 */
    	private void processFormField(FileItem item) {
    		System.out.println(item.getFieldName()+"="+item.getString());		
    	}
    
    
//-------страница примера с возможностью добавить файл
    public void cardExample (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String id = request.getParameter("id");
            
            response.setContentType("text/html;charset=UTF-8");
            
            out.println("<h1> Ваш выбранный пример </h1>");
            
            Connection con = ConnPool.getConnection();
            String sOp = "select nmdata, description from  pr.examples where id="+id;
        
            Statement op = con.createStatement();

            ResultSet resultSet = op.executeQuery(sOp);
            resultSet.next();
            String nm = resultSet.getString(1);
            String desc = resultSet.getString(2);
            
            out.println("<form action=\"clientview?act=UploadFile&id="+id+"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<p> Наименование: " +  nm + "</p>");
                      
            out.println("<p> Краткое описание: " + desc + "</p>");
                      
            out.println ("<p><input type=\"file\" name=\"f\">");
            out.println ("<input type=\"submit\" value=\"Отправить\"> </p>");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\">");
            
            ConnPool.putConnection(con);
            out.println("</form>");         
        }
        catch ( SQLException e ) {
       out.println("<p>cardExample не выполнено !!! " + e.getMessage() + "<p>") ;;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }        
    }
    
//---------загрузка названия и описания примера в БД
    public void loadExample (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Имя загружаемых данных
            String NameData = request.getParameter ("nmData");
            
            //Описание загружаемых данных
            String DescriptionData = request.getParameter ("descData");
            
            if ((request.getParameter("nmData")!= null) & (request.getParameter("descData")  != null)) {
                  
                Connection con = ConnPool.getConnection();
         
                String sOp = "insert into pr.examples(nmdata, description) values('" 
                        + NameData + "', '" + DescriptionData +"')";
               
                Statement op = con.createStatement();

                op.execute(sOp);
               
                ConnPool.putConnection(con);
            }
        }
        catch ( SQLException e ) {
         out.println("<p> loadExample не выполнено " + e.getMessage() + " </p>");
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }
    }
  
        public void deleteF (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            HttpSession session = request.getSession();
            
            String fn = (String)session.getAttribute("fn");
            System.out.println("fn = " + fn);
            String path = getServletContext().getRealPath("/upload/" + fn);
            System.out.println("path for Delete = " + path);
            File fl = new File (path);
        //    File fl = new File ("C:/Users/dashk/Documents/Course_project/proba/servInterface/build/web/upload/time_series_covid_19_confirmed.csv");
            System.out.println(fl.getAbsolutePath());
       //     fl.delete();
            if (fl.delete()) {
            System.out.println("Файл удален");
            }
            else System.out.println("Файл не найден");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void ColumnNM (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            
             String sIdex = request.getParameter("id");
            HttpSession session = request.getSession();
            
            String fname = (String)session.getAttribute("fn");
            String path = getServletContext().getRealPath("/upload/" + fname);
            File file = new File(path);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем первую строку
            String line = reader.readLine();
            
            String[] subStr;
            String delimeter = ",(?!\\s)";
            
            subStr = line.split(delimeter);
            HashMap<Integer, Integer> TitCol = new HashMap<>();
            Connection con = ConnPool.getConnection();
            
            for(int i = 0; i < subStr.length; i++) {
                
                String sOp = "insert into pr.colnm(name, idexample, num) values('" + subStr[i] + "', " + sIdex + ", " + i+") returning id";
       //        System.out.println(sOp);
                Statement op = con.createStatement();
                
                ResultSet res = op.executeQuery(sOp);
                res.next();
                int id = res.getInt(1);
       //         System.out.println("id =  "+id);
                TitCol.put(i, id);
            }
            int istr = 0;
           System.out.println("--------------------------");
            line = reader.readLine();
            while (line != null) {  
                         
                //разбиваем строку по разделителю
                subStr = line.split(delimeter);
                 int tt;
                for(int i = 0; i < subStr.length; i++) { 
                    
                    int idcol = TitCol.get(i);
                    
                    if(subStr[i].matches("\\A\\-{0,1}\\d+\\.{0,1}\\d*\\z")){
                        tt = 0;
                        String sOp = "insert into pr.tbcell(numrow, idcol, ttype, valdouble) values(" + istr + ", " + idcol + ", "+ tt +", " + subStr[i] + ") ";
                        
                        Statement op = con.createStatement();
                //  System.out.println("sOp1 = "+ sOp);
                        op.execute(sOp);
                    }
                    else {
                        tt=1;
                        String Str = subStr[i].replace('\'', ' ');
                        String sOp = "insert into pr.tbcell(numrow, idcol, ttype, valstr) values(" + istr + ", " + idcol + ", "+ tt +", '" + Str + "') ";
                   //     System.out.println("sOp2 = "+ sOp);
                        Statement op = con.createStatement();
                  
                        op.execute(sOp);
                    }
               
                }
             
               
                // считываем остальные строки в цикле
                line = reader.readLine();
                istr++;
            }
            System.out.println("--------------------------");
            ConnPool.putConnection(con);
            
            reader.close();
            fr.close();
        }
        catch ( SQLException e ) {
        out.println("<p> ColumnNM не выполнено" + e.getMessage() + "<p>") ;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }
    }
        
    public void outFile (HttpServletRequest request, HttpServletResponse response, String filenm)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String idex = request.getParameter("id");
      //      System.out.println("Вывод OutFile");
      //      String filein = request.getParameter("item");
            String path = getServletContext().getRealPath("/upload/" + filenm);
            File file = new File(path);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            String[] subStr;
            String delimeter = ",(?!\\s)";
            
/*            out.println("<p> <strong> Выберете разделитель: <br />");
            out.println("<input type=\"radio\" value=\"zap\" name=\"delim\" id=\"delim\" tabindex=\"1\" checked />Запятая <br />");
            out.println("<input type=\"radio\" value=\"tochzap\" name=\"delim\" id=\"delim\" tabindex=\"2\" />Точка с запятой <br />");
             String cmd=request.getParameter("delim");
             if(cmd.equals("zap")) {
                 delimeter = ",(?!\\s)";
             }
             else if (cmd.equals("tochzap")) {
                 delimeter = ";(?!\\s)";
             }
*/            
         //   subStr = line.split(delimeter);
           // line = reader.readLine();
            subStr = line.split(delimeter);
            
            out.println("<form action=\"clientview?act=loadinDB&id="+idex+"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type = \"submit\" value = \"Принять\" id =\"submit\" />");
            out.println("</form>"); 
            
            out.println("<form action=\"clientview?act=delFile\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type=\"submit\" id =\"btn\" value=\"Назад\">");
            out.println("</form>"); 
            
            out.println("<table wight=50% border=1>");
            out.println("<tr>"); 
            for(int i = 0; i < subStr.length; i++) {  //for убрать при out.println("<p>" + Arrays.asList(subStr) + "</p>");
                      
                    out.println("<th>");   
                    out.println( subStr[i]);
                    out.println("</th>"); 
                    
            }
            out.println("</tr>");
                            
            line = reader.readLine();
              
            while (line != null) {  
                         
                //разбиваем строку по разделителю
                subStr = line.split(delimeter); //вкл., если out.println("<p>" + subStr[i] + "</p>"); и out.println("<p>" + Arrays.asList(subStr) + "</p>");
                out.println("<tr>"); 
                
                for(int i = 0; i < subStr.length; i++) {  //for убрать при out.println("<p>" + Arrays.asList(subStr) + "</p>");
                    
                    out.println("<td>");    
                    out.println( subStr[i] );
                    out.println("</td>");
                }
                
                out.println("</tr>");
             //   out.println("<p>" + Arrays.asList(subStr) + "</p>");
               
                // считываем остальные строки в цикле
                line = reader.readLine(); //вкл., если out.println("<p>" + subStr[i] + "</p>"); и out.println("<p>" + Arrays.asList(subStr) + "</p>");
            }
            
            out.println("</table>");
            reader.close();
            fr.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            open(request, response);
                    
           if(request.getParameter("act") != null) {
               //нажатие на "Добавить новый пример"  
               if (request.getParameter("act").equals("addexampleform")) {
                   //ввод нового примера
                   newExample(request, response);
               }
                                
                if(request.getParameter("act").equals("addexample")) {
                   //загрузка в БД названия и описания примера 
                   loadExample (request, response);
                   response.sendRedirect("clientview");                    
                }
                
                //карточка примера с загрузкой файла
                if(request.getParameter("act").equals("cardexample")) {
                    cardExample (request, response);
                }
                //загрузка файла на сервер
                if(request.getParameter("act").equals("UploadFile")){
                    UploadFile(request, response);
                 //   outFile(request, response);
                 //   response.sendRedirect("clientview");
                }
                
                //загрузка файла в БД
                if(request.getParameter("act").equals("loadinDB")){
                     ColumnNM(request, response);
                 }
                
                //удаление файла из сервера
                if(request.getParameter("act").equals("delFile")){ 
                    deleteF(request, response);
                }
                
            }
           
            else startForm(request, response) ;
                                    
            end(request, response);
                
        }
    }
  
    @Override
    public void init(ServletConfig config) throws ServletException  {
        
        super.init(config);
        
      int N = Integer.parseInt(config.getInitParameter("poolsize"));
      
      String sUser = config.getInitParameter("user");
      String sPassw = config.getInitParameter("password");
      String sSrvNm = config.getInitParameter("serverdb");
      String sPort = config.getInitParameter("portdb");
      String sDbNm = config.getInitParameter("dbname");
      
      System.out.println("-------------------------");
      System.out.println("sSrvNm = " + sSrvNm);
//      System.out.println("proba = " + config.getInitParameter("proba"));
      System.out.println("servlet name = " + config.getServletName());
      
      String sURL = "jdbc:postgresql://" + sSrvNm + ":" + sPort + "/" + sDbNm;
        System.out.println("URL = " + sURL);
      try {
          ConnPool = new DbPool ( sURL, sUser, sPassw, N );
          ConnPool.fillPool();
      }
      catch ( ClassNotFoundException ex ) {
          throw new ServletException ( ex.getMessage() );
      }
      catch ( SQLException e1 ) {
          throw new ServletException ( e1.getMessage() );
      }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
