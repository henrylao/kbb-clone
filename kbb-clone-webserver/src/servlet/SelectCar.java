package servlet;

import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.PrintWriter;
//import java.net.Socket;

//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import adapter.BuildAuto;
//import client.AutoClient;
import client.ClientToServerSocketThread;
//import model.Automobile;

/**
 * Servlet implementation class carList
 */
@WebServlet("/SelectCar")
public class SelectCar extends HttpServlet {
	private static final long serialVersionUID = 1L;

//  private AutoServer server;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " + "Transitional//EN\">";

	public static String headWithTitle(String title) {
		return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}


	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// GET COMMAND to be called by servlet
		int port = 1234;
		String host = "127.0.0.1";
		ClientToServerSocketThread clientThread = new ClientToServerSocketThread(host, port, 2);
		new Thread(clientThread).start();
		while (clientThread.isAlive()) {
			try {
				new Thread().sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread is still running");
		}
		// create string array from string
		String[] models = ((String) clientThread.getData()).split(",");
		System.out.println("Fetched: " + models);

//		Automobile a1 = (Automobile) clientThread.getData();

//		System.out.println(a1.toString());

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.setAttribute("models", models);
		String title = "Select a Car";
		String toHtml = "";
		out.println(headWithTitle(title) + "<BODY BGCOLOR=\"#AED6F1\">\n" + "<H1 ALIGN=\"CENTER\">" + title + "</H1>");
		out.println("<form method=\"post\" action=\"SelectCarOptions\"  ALIGN=\"CENTER\">");
		for (int j = 0; j < models.length; j++) {
			toHtml += "<input type=\"radio\" name=\"carName\" value=\"" + models[j] + "\" ";
			if (j == 1)
				toHtml += "checked";
			toHtml += ">" + models[j] + "</input><br>";
		}
		HttpSession session = request.getSession();
		toHtml += "<p><input align=center type=\"submit\" value=\"Submit\"></p>";
		toHtml += "</form></BODY></HTML>";
		out.write(toHtml);
	}

}