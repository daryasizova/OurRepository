/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servl;

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
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
/**
 *
 * @author dashk
 */
public class readcsv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet readfile</title>");  
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<form name = \"form1\" action= \"readcsv1\" method=\"post\">");
            
            out.println("<p> Наименование </p>");
            out.println("<p> <input type = \"text\" name = \"nmData\"> </p>");
            
            out.println("<p> Краткое описание: </p>");
            out.println("<p> <input type = \"text\" name = \"descData\" /> </p>");
            
            out.println("<p> <input type = \"file\" encrypt = \"multipart/form-data\"  name=\"fl\" /> </p>");
            out.println("<input type = \"submit\"  value = \"отправить\" id =\"submit\" />");
                        
            out.println("</form>");
            
        //    @WebServlet (name = "viewController", urlPatterns = {"/view/*"};
           // if(form1=true){}
            
            //Имя загружаемых данных
            String NameData = request.getParameter ("nmData");
            /*-----Ввод в БД--------*/
            
            //Описание загружаемых данных
            String DescriptionData = request.getParameter ("descData");
            /*-----Ввод в БД--------*/
            
            
            String filein = request.getParameter("fl");
            File file = new File(filein);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            String[] subStr;
            
           // char [] ArrStr = line.toCharArray();
            
            String delimeter = ",(?!\\s)";
       //     String notDellim = ", ";
            
         //   subStr = line.split(delimeter);
            
           // line = reader.readLine();
            subStr = line.split(delimeter);
            
            out.println("<h1>Название: " + NameData + "</h1>");
            out.println("<h2Краткое описание: >" + DescriptionData + "</h2>");
            
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
                
                //проба игнорирования ", "
                
                
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
            out.println("</body>");
            out.println("</html>");
            
        }    
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
