/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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
             out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/>");
          
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
            out.println("<div class=\"header-h1\">");
            out.println("<h3> Список Ваших примеров: </h3> ");
            out.println("</div>");
            
            out.println("<div  class=\"container4\">");
            out.println("<div class=\"buttonauto\">");
            out.println("<input type = \"submit\"  value = \"Добавить новый\" id =\"submit\" />");
            out.println("</div>");
            //обращение к БД
            Connection con = ConnPool.getConnection();
            String sOp = "select id, nmdata, description from  pr.examples";
        
            Statement op = con.createStatement();

            ResultSet resultSet = op.executeQuery(sOp);
            //вывод из БД названий и описаний с ссылками
             out.println("<table class=\"table1\">");
         //   out.println("<table wight=\"70%\">");
            out.println("<tr><th>№</th><th>Название</th><th>Краткое описание</th></tr>");
          //  out.println("<th>");
          //  for (int i=0; i<c; i++) {
                while (resultSet.next()){
            //    out.println("<p> <a href = \" http://localhost:8084/servInterface/clientview?act=cardexample&id= "+ c +"\">");
                    int id = resultSet.getInt(1);
                    String nm = resultSet.getString(2);
                    String desc = resultSet.getString(3);
               //     out.println("<tr><td>");
                    out.println("<tr>");
                    out.println("<td><a href=\"clientview?act=cardexample&id=" + id + "\">"+ id + "</td><td></a><a href=\"clientview?act=cardexample&id=" + id + "\">" + nm  + "</td><a href=\"clientview?act=cardexample&id=" + id + "\"><td></a><a href=\"clientview?act=cardexample&id=" + id + "\">" + desc +"</td></a></tr>");
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
            out.println("<div  class=\"container\">");
            out.println("<form name = \"newExample\" action= \"clientview?act=addexample\" method=\"post\">");
            out.println("<div class=\"header-h1 header-h1\">");
            out.println("<h1> Ввод названия и описания примера </h1>");
            out.println("</div> <br>");
            
            out.println("<div class=\"container2\" align=\"left\">");
            out.println("<div class=\"container3\">");
            out.println("<div class=\"сontainer-text\">");
            out.println("<p> Наименование: </p> ");
            out.println("</div> ");
                     
            out.println(" <input type = \"text\" name = \"nmData\"> ");
            out.println("</div> ");
            
            out.println("<div class=\"container3\">");
            out.println("<div class=\"сontainer-text\">");
            out.println("<p> Краткое описание: </p>");
            out.println("</div> ");
            out.println(" <input type = \"text\" name = \"descData\" />");
            out.println("</div> ");
            
            out.println("<div class=\"container-button\" align=left>");
            out.println("<input type = \"submit\" value = \"Сохранить\" id =\"submit\" />");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\">");
            out.println("</div> "); 
            out.println("</div> ");
            out.println("</form>"); 
            out.println("</div> ");
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
                String sId = request.getParameter("id");
                
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
    
    
    public void newUpload (HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
    		//проверяем является ли полученный запрос multipart/form-data
    		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    		if (!isMultipart) {
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    			return;
    		}
                String tId = request.getParameter("idt");
                
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
                         session.setAttribute("newfl", item.getName());
                        outFileNew ( request,  response,  item.getName());
    		} catch (Exception e) {
    			e.printStackTrace();
    			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
    		}		
    	}
    
    public void outFileNew (HttpServletRequest request, HttpServletResponse response, String filenm)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String id = request.getParameter("id");
            String idt = request.getParameter("idt");
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

            subStr = line.split(delimeter);
            
            out.println("<form action=\"clientview?act=calcPred&id="+id+"&idt="+idt+"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type = \"submit\" value = \"Рассчитать\" id =\"submit\" />");
            out.println("</form>"); 
            
            out.println("<form action=\"clientview?act=delNew&id="+id+"&idt="+idt+"\" method=\"post\" enctype=\"multipart/form-data\">");
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
      
           int num = 0;
            line = reader.readLine();
              
            while ((line != null) && (num < 10) ) {  
                   num++;      
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
    
    
//-------страница примера с возможностью добавить файл
    public void cardExample (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //id Примера
            String id = request.getParameter("id");
            
            response.setContentType("text/html;charset=UTF-8");
            
            out.println("<div class=\"center\">");
            out.println("<div class=\"header-h1 header-h1\">");
            out.println("<h1> Ваш выбранный пример </h1>");
            out.println("</div>");
            
            Connection con = ConnPool.getConnection();
            String sOp = "select nmdata, description from  pr.examples where id="+id;
        
            Statement op = con.createStatement();
            
            ResultSet resultSet = op.executeQuery(sOp);
            resultSet.next();
            String nm = resultSet.getString(1);
            String desc = resultSet.getString(2);
            
            out.println("<p class=\"style-p2\"> Наименование: " +  nm + "</p>");
                      
            out.println("<p class=\"style-p2\"> Краткое описание: " + desc + "</p>");
            
            String sOps = "select id from  pr.colnm where idexample="+id;
  //           Statement op = con.createStatement();
           ResultSet resId = op.executeQuery(sOps);
           
            if (resId.next()) {
                                    
                out.println("<h3>Ваши данные</h3>");
                out.println("<div class=\"line1\">");
                //выборка заголовков
                String qOp = "select name from  pr.colnm where idexample="+id +" order by num";
                 ResultSet resCol = op.executeQuery(qOp);
                 
                out.println("<table class=\"table2\">");
                out.println("<tr>"); 
                while (resCol.next()) { 
                      String nmcol = resCol.getString(1);
                    out.println("<th>");   
                    out.println( nmcol);
                    out.println("</th>"); 
                }
                out.println("</tr>");
                int ttype;
                String valStr;
                int valDoub;
                
                boolean pr = true;
                int iR = 0;
                int num=0;
                while (pr && num<10){
                    num++;
                    String sqOp = "select ttype, valstr, valdouble from pr.tbcell, pr.colnm"
                        + " where pr.tbcell.idcol=pr.colnm.id and pr.colnm.idexample="+id+" and pr.tbcell.numrow="+iR+" order by pr.colnm.num";
                    
                    ResultSet resRow = op.executeQuery(sqOp);
                    out.println("<tr>");
                    while (resRow.next()) {
                        ttype = resRow.getInt(1);
                        valStr = resRow.getString(2);
                        valDoub = resRow.getInt(3);

                        if(ttype == 1){
                            out.println("<td>");   
                            out.println( valStr);
                            out.println("</td>");
                        }
                        else {
                            out.println("<td>");   
                            out.println( valDoub);
                            out.println("</td>");
                        }                    
                        iR++;
                    }
                out.println("</tr>");
                }                
                out.println("</table>");
                out.println("</div>");
                
              //  out.println("<div class=\"header-h1\">"); 
                out.println("<h3>Список Ваших опытов</h3>"); 
            //    out.println("</div>");
                //вывод списка сохраненных опытов
                String ssOp = "select id, nametask, descrtask from  pr.task where idexample="+id;
                ResultSet restask = op.executeQuery(ssOp);
                
                while(restask.next()) {
                    int idt = restask.getInt(1);
                    String nmt = restask.getString(2);
                    String desct = restask.getString(3);
                    
                    out.println("<p class=\"style-p\"><a href=\"clientview?act=cardtask&id="+id+"&idt="+idt +"\">" + nmt+ "     " +desct + "</a></p>"); 
                }
                
                out.println("<form action=\"clientview?act=addtaskform&id="+id+"\" method=\"post\" enctype=\"multipart/form-data\">");               
                out.println ("<input type=\"submit\" value=\"Добавить опыт\">");
                out.println("</form>");  
                
            }
            else {
                out.println("<form action=\"clientview?act=UploadFile&id="+id+"\" method=\"post\" enctype=\"multipart/form-data\">");          
                out.println ("<p><input type=\"file\" name=\"f\">");
                out.println ("<input type=\"submit\" value=\"Предпросмотр\"> </p>");
                out.println("</form>");  
            }
            
            out.println("<form action=\"clientview?act=startform\"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type=\"submit\" value=\"На главную\">");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            ConnPool.putConnection(con);
                     
        }
        
        catch ( SQLException e ) {
       out.println("<p>cardExample не выполнено !!! " + e.getMessage() + "<p>") ;;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }        
    }
    
    //---------Карточка опыта с выбором метода
    public void cardTask (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
                response.setContentType("text/html;charset=UTF-8");
                //id опыта
                String sId = request.getParameter("idt");
                //id примера
                String id = request.getParameter("id");
                
                out.println("<div class=\"center\">");
                out.println("<div class=\"header-h1 header-h1\">");
                out.println("<h1> Выбранный опыт </h1>");
                out.println("</div>");
                
                Connection con = ConnPool.getConnection();
                String sOp = "select nametask, descrtask from  pr.task where id="+sId;

                Statement op = con.createStatement();

                ResultSet resultSet = op.executeQuery(sOp);
                resultSet.next();
                String nm = resultSet.getString(1);
                String desc = resultSet.getString(2);

                out.println("<p class=\"style-p2\">Название опыта: " +  nm + "</p>");

                out.println("<p class=\"style-p2\"> Краткое описание опыта: " + desc + "</p>");

                String idM = request.getParameter("meth");

                String check = "select idcol from pr.usrcol where idtask="+sId;
                ResultSet checkSet = op.executeQuery(check);
            //    checkSet.next();
                if(checkSet.next()) {

                    String chosenDt = "select cl.name from pr.usrcol uc, pr.colnm cl where uc.idcol=cl.id and maindt=0 and idtask="+sId;
                    ResultSet chosenD = op.executeQuery(chosenDt);
                    chosenD.next();
                    String dtMain = chosenD.getString(1);
                    out.println("<p class=\"style-p\"> Целевые данные: " + dtMain + "</p>");

                    String chos = "select cl.name from pr.usrcol uc, pr.colnm cl where uc.idcol=cl.id and maindt=1 and idtask="+sId;
                    ResultSet chosD = op.executeQuery(chos);

                    out.println("<p class=\"style-p\"> Выбранные данные: " );
                    while (chosD.next()){
                        String dt = chosD.getString(1);
                         out.println( dt );
                    }
                     out.println( "</p>" );

                     int i=-1;
                    String resTr = "select mark, val from pr.res where idtask="+sId;
                    ResultSet resSet = op.executeQuery(resTr);
                    out.println("<h4>Результат обучения:</h4>");
                    while (resSet.next()){
                        int mark = resSet.getInt(1);
                        double valT = resSet.getDouble(2);
                        if (mark == 0){
                            out.println("<p class=\"style-p\">  Независимый параметр : "+valT+"</p>");
                        }
                        else if (mark == 1) {
                            out.println("<p class=\"style-p\">  R2: "+valT+"</p>");
                        }
                        else out.println("<p class=\"style-p\"> Коэффициент "+i+": "+valT+"</p>");
                        i++;
                    }
                    
                    out.println("<h4>Загрузка файла для прогнозирования</h4>");
                    //Загразка файла для прогнозирования
                    out.println("<form action=\"clientview?act=newUpload&id="+id+"&idt="+sId+"\" method=\"post\" enctype=\"multipart/form-data\">");          
                    out.println ("<p><input type=\"file\" name=\"f\">");
                    out.println ("<input type=\"submit\" value=\"Предпросмотр\"> </p>");
                    out.println("</form>"); 
                    
                }
                else {
                    //если метод обучения выбран
                    String checkMeth = "select idmeth from pr.task where idmeth is not null and id="+sId;
                    ResultSet chM = op.executeQuery(checkMeth);
                    
                    if(chM.next()){
                         out.println("<h4> Выберете столбец с целевыми данными:</h4>");
                         String mOp = "select id,name from pr.colnm where idexample="+id; //вывод названий столбцов
                         ResultSet rMeth = op.executeQuery(mOp);

                         out.println("<form action= \"clientview?act=domet&id="+id+"&idt="+sId+"\" method=\"post\">");
                         out.println("<select name=\"maindt\" id=\"maindt\" size=\"1\">");
                         out.println("<option value=\"null\" selected>--Выберете колонку--</option>");              
                         while (rMeth.next()) {
                             int idCol = rMeth.getInt(1);
                             String nmCol = rMeth.getString(2);
                             out.println("<option value=\""+idCol+"\">"+ nmCol+ "</option>");
                         }     
                         out.println("</select>");

                         out.println("<br>");
                         ResultSet reMeth = op.executeQuery(mOp);
                         out.println("<br>");
                         out.println("<h4>Выберете данные для обработки:</h4>");
                        out.println("<div class=\"divcl\">");
                        
                        while (reMeth.next()) {
                                int idC = reMeth.getInt(1);
                                String nmC = reMeth.getString(2);
                                out.println("<label class=\"contain\">");
                                out.println("<input type=\"checkbox\" name=\"nmCol\" value="+idC+">"+nmC);
                                out.println("<span class=\"checkmark\"></span>");
                                out.println("</label>");
                         }
                         out.println("<br>");
                         out.println("</div>");
                         out.println("<p><input type = \"submit\" value = \"Принять\" id =\"submit\" /></p>");
                         out.println("</form>");
                     }
                     //если метод обучения НЕ выбран
                    else { 
                         out.println("<br>");
                         out.println("<h4> Выберете метод машинного обучения</h4>");
                 //        out.println("<div class=\"select\">");
                         //выбор метода обучения 
                         String qOp = "select id,name from pr.method";
                         ResultSet resMeth = op.executeQuery(qOp);

                         out.println("<form action= \"clientview?act=method&id="+id+"&idt="+sId +"\" method=\"post\">");
                         out.println("<select name=\"meth\" id=\"meth\" size=\"1\">");
                         out.println("<option value=\"null\" selected>--Выберете метод--</option>");
                         while (resMeth.next()) {
                             int idMet = resMeth.getInt(1);
                             String meth = resMeth.getString(2);
                             out.println("<option value=\""+idMet+"\">"+ meth+ "</option>");
                         }     
                     out.println("</select>");

                     //    out.println("<br>");
                     //   out.println("<form name = \"method\" action= \"clientview?act=domethod\" method=\"post\">");
                     //   out.println("<div class=\"container-button\" align=left>");
                        out.println("<input type = \"submit\" value = \"Принять\" id =\"submit\" />");
                       // out.println("</div>");
                       out.println("</form>");
                  //      out.println("</div>");
                        out.println("</div>");
                     }

                }
                out.println("<form action=\"clientview?act=cardexample&id="+id+"\" method=\"post\" enctype=\"multipart/form-data\">");
                out.println("<input type=\"submit\" value=\"Назад\">");
                out.println("</form>");

                ConnPool.putConnection(con);
            }
        catch ( SQLException e ) {
       out.println("<p>cardTask не выполнено !  " + e.getMessage() + "<p>") ;;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }    
    }
    
    public void loadIdMeth (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            String Id = request.getParameter("id"); //пример
            String tId = request.getParameter("idt"); //опыт
            
            String idM = request.getParameter("meth");
            System.out.println ("Id= " + Id + " tId= "+tId +" idM= "+idM);
           
                Connection con = ConnPool.getConnection();
         //--------Ввод в БД
                String sOp = "update pr.task set idmeth="+idM+" where id="+tId;
               
                Statement op = con.createStatement();

                op.execute(sOp);
               
                ConnPool.putConnection(con) ;
                response.sendRedirect("clientview?act=cardtask&id="+Id+"&idt="+tId);
            
            
        }
        catch ( SQLException e ) {
         out.println("<p> loadTask не выполнено " + e.getMessage() + " </p>");
        }
        catch ( InterruptedException ei ) {
          ei.getMessage();
        }
    }
    
    //Ввод в БД данных для обучения
    public void inDB (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
        try{
        String maindt = request.getParameter("maindt");//целевой столбец (ответ пользователя)        
        String idt = request.getParameter("idt"); //id опыта
        String [] col = request.getParameterValues("nmCol"); //массив выбранных столбцов
        
     //   int cCol [] = Integer.parseInt(col);
        Connection con = ConnPool.getConnection();
        Statement op = con.createStatement();
        String qOp = "insert into pr.usrcol values ("+idt+","+maindt+",0)"; //ввод целевого столбца     
         op.execute(qOp);
        
        for (int i = 0; i < col.length; i++) {
             System.out.println(col[i]); 
            String rOp = "insert into pr.usrcol values ("+idt+","+col[i]+",1)";
            op.execute(rOp);
        }
   
        ConnPool.putConnection(con);
       // out.println("<p>Занесение в БД столбцов InDB ЗАВЕРШЕННО</p>");
        MethodML(request,response);
        }
        catch ( SQLException e ) {
       out.println("<p>inDB не выполнено !  " + e.getMessage() + "<p>") ;;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }
    }
    
//-------Выполнение скрипта ОБУЧЕНИЯ Python 
    public void MethodML (HttpServletRequest request, HttpServletResponse response)
          /*  throws ServletException, IOException, InterruptedException */  {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String sId = request.getParameter("id");
            String idT = request.getParameter("idt");
            
            Connection con = ConnPool.getConnection();
            Statement op = con.createStatement();
            //id выбранного метода
            String checkMeth = "select idmeth from pr.task where id="+idT;
            ResultSet chM = op.executeQuery(checkMeth);
            chM.next();
            String idMet = chM.getString(1);
        //    out.println ("<p>idMet= "+idMet+"</p>");
          
            // указываем в конструкторе ProcessBuilder,
            // что нужно запустить программу ls с параметрами -l /dev
            ProcessBuilder procBuilder = new ProcessBuilder("python3", "/home/user/Works/o1.py", sId, idMet, idT);
            
            // перенаправляем стандартный поток ошибок на
            // стандартный вывод
            procBuilder.redirectErrorStream(true);

            // запуск программы
            Process process = procBuilder.start();
            // читаем стандартный поток вывода
        // и выводим на экран
        InputStream stdout = process.getInputStream();
        InputStreamReader isrStdout = new InputStreamReader(stdout);
        BufferedReader brStdout = new BufferedReader(isrStdout);
          
        ConnPool.putConnection(con);
        // ждем пока завершится вызванная программа
        // и сохраняем код, с которым она завершилась в 
        // в переменную exitVal
        int exitVal = process.waitFor();
        response.sendRedirect("clientview?act=cardtask&id="+sId+"&idt="+idT);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }                    
    }
        
    //-------Выполнение скрипта ПРЕДСКАЗАНИЯ Python 
    public void PredictionML (HttpServletRequest request, HttpServletResponse response)
          /*  throws ServletException, IOException, InterruptedException */  {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Connection con = ConnPool.getConnection();
            Statement op = con.createStatement();
            
            HttpSession session = request.getSession();
            
            String sId = request.getParameter("id");
            String idT = request.getParameter("idt");

            //id выбранного метода
           // String idMet = request.getParameter("meth");
            String checkMeth = "select idmeth from pr.task where id="+idT;
            ResultSet chM = op.executeQuery(checkMeth);
            chM.next();
            String idM = chM.getString(1);
             
            //путь к файлу
            String fname = (String)session.getAttribute("newfl");
            String path = getServletContext().getRealPath("/upload/" + fname);
            
            String outPath = getServletContext().getRealPath("/upload/result.csv");
            
            // указываем в конструкторе ProcessBuilder,
            // что нужно запустить программу ls с параметрами -l /dev
            ProcessBuilder procBuilder = new ProcessBuilder("python3", "/home/user/Works/o2.py", sId, idM, idT, path, outPath);
            
            // перенаправляем стандартный поток ошибок на
            // стандартный вывод
            procBuilder.redirectErrorStream(true);

            // запуск программы
            Process process = procBuilder.start();
            // читаем стандартный поток вывода
            // и выводим на экран
            InputStream stdout = process.getInputStream();
            InputStreamReader isrStdout = new InputStreamReader(stdout);
            BufferedReader brStdout = new BufferedReader(isrStdout);
          
        
            // ждем пока завершится вызванная программа
            // и сохраняем код, с которым она завершилась в 
            // в переменную exitVal
            int exitVal = process.waitFor();
            
            outPredFile(request,response);
     //       response.sendRedirect("clientview?act=cardtask&id="+sId+"&idt="+idT);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }                    
    }
    
    //вывод файла с результатом
    public void outPredFile (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String id = request.getParameter("id");
            String idt = request.getParameter("idt");
      //      System.out.println("Вывод OutFile");
      //      String filein = request.getParameter("item");
            String path = getServletContext().getRealPath("/upload/result.csv");
            File file = new File(path);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            String[] subStr;
            String delimeter = ",(?!\\s)";

            subStr = line.split(delimeter);
            out.println("<div class=\"center\">");
            out.println("<div class=\"header-h1 header-h1\">");
            out.println("<h1> Результат прогноза </h1>");
            out.println("</div>");
            out.println("<a href=\"http://192.168.0.108:8080/servInterface/upload/result.csv\" download=\"\">");
           // out.println("<form action=\"clientview?act=calcPred&id="+id+"&idt="+idt+"\" method=\"post\" enctype=\"multipart/form-data\">");
         //   out.println("<a href=" + path + " download=\"\">");
            out.println("<input type = \"submit\" value = \"Скачать файл\" id =\"submit\" />");
            out.println("</a>");             
            //out.println("</form>"); 
            
            out.println("<form action=\"clientview?act=cardtask&id="+id+"&idt="+idt+"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type=\"submit\" id =\"btn\" value=\"Ок\">");
            out.println("</form>"); 
            
            out.println("<table wight=50% border=1>");
            out.println("<tr>"); 
            for(int i = 0; i < subStr.length; i++) {  //for убрать при out.println("<p>" + Arrays.asList(subStr) + "</p>");
                      
                    out.println("<th>");   
                    out.println( subStr[i]);
                    out.println("</th>"); 
                    
            }
            out.println("</tr>");
      
           int num = 0;
            line = reader.readLine();
              
            while ((line != null) && (num < 10) ) {  
                   num++;      
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
            out.println("</div>");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//---------Ввод названия и описания опыта
    public void newTask (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String sId = request.getParameter("id");
            
            out.println("<div class=\"container\">");
            
            out.println("<form name = \"newTask\" action= \"clientview?act=addtask&id=" + sId + "\" method=\"post\">");
            out.println("<div class=\"header-h1 header-h1-left\">");
            out.println("<h1> Ввод названия и описания примера </h1>");
            out.println("</div> <br>");
            
            out.println("<div class=\"container2\" align=\"left\">");
            out.println("<div class=\"container3\">");
            out.println("<div class=\"сontainer-text\">");
            out.println("<p> Название опыта: ");
            out.println("</div>");
            
            out.println(" <input type = \"text\" name = \"nmTask\"> </p>");
            out.println("</div>");
            
            out.println("<div class=\"container3\">");
            out.println("<div class=\"сontainer-text\">");
            out.println("<p> Краткое описание: ");
            out.println("</div>");
            
            out.println(" <input type = \"text\" name = \"descTask\" /> </p>");
            out.println("</div>");
            
            out.println("<div class=\"container-button\" align=left>");
            out.println("<input type = \"submit\" value = \"Сохранить\" id =\"submit\" />");
            out.println("<input type=\"button\" onclick=\"history.back();\" value=\"Назад\">");
            out.println("</div>");  
            out.println("</div>");
            out.println("</form>");   
            out.println("</div>");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadTask (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            String sId = request.getParameter("id");
            
            //Имя опыта
            String NameTask = request.getParameter ("nmTask");
            
            //Описание опыта
            String DescriptionTask = request.getParameter ("descTask");
            
            if ((request.getParameter("nmTask")!= null) && (request.getParameter("descTask")  != null)) {
                  
                Connection con = ConnPool.getConnection();
         //--------Ввод в БД
                String sOp = "insert into pr.task(nametask, descrtask,idexample) values('" 
                        + NameTask + "', '" + DescriptionTask +"', " + sId + ")";
               
                Statement op = con.createStatement();

                op.execute(sOp);
               
                ConnPool.putConnection(con) ;
                response.sendRedirect("clientview?act=cardexample&id="+sId);
            }
            
        }
        catch ( SQLException e ) {
         out.println("<p> loadTask не выполнено " + e.getMessage() + " </p>");
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
            
            if ((request.getParameter("nmData")!= null) && (request.getParameter("descData")  != null)) {
                  
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
    
    //---------Удаление прогнозного файла с сервера
        public void deleteNew (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String id = request.getParameter("id");
            String idt = request.getParameter("idt");
            HttpSession session = request.getSession();
            
            String fn = (String)session.getAttribute("fn");
            System.out.println("fn = " + fn);
            String path = getServletContext().getRealPath("/upload/" + fn);
            System.out.println("path for Delete = " + path);
            File fl = new File (path);
       
            System.out.println(fl.getAbsolutePath());
     
            if (fl.delete()) {
            System.out.println("Файл удален");
            }
            else System.out.println("Файл не найден");
            
            response.sendRedirect("clientview?act=cardtask&id="+id+"&idt="+idt);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  //---------Удаление файла с сервера
        public void deleteF (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String sIdex = request.getParameter("id");
            HttpSession session = request.getSession();
            
            String fn = (String)session.getAttribute("fn");
            System.out.println("fn = " + fn);
            String path = getServletContext().getRealPath("/upload/" + fn);
            System.out.println("path for Delete = " + path);
            File fl = new File (path);
       
            System.out.println(fl.getAbsolutePath());
     
            if (fl.delete()) {
            System.out.println("Файл удален");
            }
            else System.out.println("Файл не найден");
            
            response.sendRedirect("clientview?act=cardexample&id="+sIdex);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//---------Заполнение БД данными из файла    
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
            response.sendRedirect("clientview?act=cardexample&id="+sIdex);
        }
        catch ( SQLException e ) {
        out.println("<p> ColumnNM не выполнено" + e.getMessage() + "<p>") ;
      }
      catch ( InterruptedException ei ) {
          ei.getMessage();
      }
    }
//------------Вывод содержимого файла на экран       
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

            subStr = line.split(delimeter);
            
            out.println("<form action=\"clientview?act=loadinDB&id="+idex+"\" method=\"post\" enctype=\"multipart/form-data\">");
            out.println("<input type = \"submit\" value = \"Принять\" id =\"submit\" />");
            out.println("</form>"); 
            
            out.println("<form action=\"clientview?act=delFile&id="+idex+"\" method=\"post\" enctype=\"multipart/form-data\">");
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
      
           int num = 0;
            line = reader.readLine();
              
            while ((line != null) && (num < 10) ) {  
                   num++;      
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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
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
                }
                
                //загрузка файла в БД
                if(request.getParameter("act").equals("loadinDB")){
                     ColumnNM(request, response);
                 }
                
                //удаление файла из сервера
                if(request.getParameter("act").equals("delFile")){ 
                    deleteF(request, response);
                }
                //переход на стартовую страницу
                if(request.getParameter("act").equals("startform")){ 
                    startForm(request, response);
                }
                //создание нового опыта
                if(request.getParameter("act").equals("addtaskform")){ 
                    newTask(request, response);
                }
                //загрузка нового опыта в БД
                if(request.getParameter("act").equals("addtask")){ 
                    loadTask(request, response);
                }
                //карточка опыта
                if(request.getParameter("act").equals("cardtask")){ 
                    cardTask(request, response);
                }
                //выбор метода и переход на выбор столбцов
                if(request.getParameter("act").equals("domethod")){ 
                    cardTask(request, response);
                }
                //занесение в БД и запуск скрипта Python
                if(request.getParameter("act").equals("domet")){ 
                    inDB(request, response);
                  //  MethodML(request, response);
                }
                //загрузка файла для прогноза на сервер
                if(request.getParameter("act").equals("newUpload")){ 
                    newUpload(request, response);
                }
                //удаление прогнозного файла из сервера
                if(request.getParameter("act").equals("delNew")){ 
                    deleteNew(request, response);
                }
                //загрузка в БД id метода
                if(request.getParameter("act").equals("method")){ 
                    loadIdMeth(request, response);
                }
                //Расчет прогноза
                if(request.getParameter("act").equals("calcPred")){ 
                    PredictionML(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(clientview.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(clientview.class.getName()).log(Level.SEVERE, null, ex);
        }
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
