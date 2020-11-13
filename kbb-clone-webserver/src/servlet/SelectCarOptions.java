package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.ClientToServerSocketThread;
import model.Automobile;

/**
 * Servlet implementation class SelectCar
 */
@WebServlet("/SelectCarOptions")
public class SelectCarOptions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 5.0 " + "Transitional//EN\">";

	public static String headWithTitle(String title) {
		return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectCarOptions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// GET COMMAND to be called by servlet
		String carSelected = request.getParameter("carName");
		System.out.println("/SelectCarOption --> User selected: " + carSelected);
		int port = 1234;
		String host = "127.0.0.1";
		ClientToServerSocketThread clientThread = new ClientToServerSocketThread(host, port, 1, carSelected);
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
		// specific automobile fetched by key from server
		Automobile a = (Automobile) clientThread.getData();

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = "Configure " + carSelected;
		out.println(headWithTitle(title) + "<BODY BGCOLOR=\"#AED6F1\">\n" + "<H1 ALIGN=\"CENTER\">" + title + "</H1>\n"
				+ "<form action=\"CalculateTotalCost.jsp\" ALIGN=\"CENTER\" method=post>"
				+ "<TABLE BORDER=2 ALIGN=\"CENTER\" target=\"_blank\">\n" + "<TR BGCOLOR=\"#AED6F1\">\n");
		out.println(String.format("<tr><td>Make/Model:<td>%s %s</tr>", a.getMake(), a.getModel()));
		String[] optionSetNames = a.getOptionSetNames().split(",");
		for (int m = 0; m < a.getLengthOfOptionSets(); m++) {
			String[] optionNames = a.getAllOptionNamesOfAnOptionSet(m).split(",");
			out.println("<TR><TD>" + optionSetNames[m] + "<TD><select name=\"" + optionSetNames[m] + "\">");
			System.out.println("Select Car Options for: " + optionSetNames[m]);
//			System.out.println(a.getOptionChoice(optionSetNames[m]));			
			for (int n = 0; n < optionNames.length; n++) {
				out.println("<option value=\"" + optionNames[n] + "\">" + optionNames[n] + "</option>");
			}
			out.println("</select></tr>");
		}
		out.println("<td colspan=2 align=right><input align=center type=\"submit\" value=\"Done\"></td>");
		out.println("</form></TABLE></BODY></HTML>");
		session.setAttribute("configuredCar", a);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
