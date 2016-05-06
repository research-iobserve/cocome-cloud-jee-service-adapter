package cocome.cloud.sa.serviceprovider.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.apache.commons.codec.binary.Base64;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;

import de.kit.ipd.java.utils.io.Utilities;
import de.kit.ipd.java.utils.xml.JAXBEngine;

import cocome.cloud.sa.serviceprovider.ServiceProvider;
import cocome.cloud.sa.serviceprovider.ServiceProviderCatalog;
import cocome.cloud.sa.serviceprovider.ServiceProviderObjectFactory;

/**
 * Provides all service provider
 */
@WebServlet("/ServiceProviderInterface")
public class ServiceProviderInterface extends HttpServlet {

	public static final String URL_SERVICE_BASE = "/Services";

	private static final long serialVersionUID = 1L;

	private static final String PATH_TYPES_SCHEMA = "/WEB-INF/schemas/types.xsd";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServiceProviderInterface() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	@GET
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();

		// TODO later..for testing purposes disabled
		// if(!checkUser(request,response)){
		// return;
		// }

		final String urlBase = this.getBaseUrl(request);

		final ServiceProviderCatalog catalog = new ServiceProviderCatalog();

		final ServiceProvider spDatabase = new ServiceProvider();
		spDatabase.setName(ServiceProviderDatabase.NAME_SERVICE_PROVIDER_DATABASE);
		spDatabase.setUrl(urlBase
				+ URL_SERVICE_BASE
				+ ServiceProviderDatabase.URL_SERVICE_PROVIDER_DATABASE);

		final ServiceProvider spCashier = new ServiceProvider();
		spCashier.setName("BookSale");
		spCashier.setUrl(urlBase
				+ URL_SERVICE_BASE
				+ "/BookSale/ServiceProviderBookSale");

		catalog.getListServiceProvider().add(spCashier);
		catalog.getListServiceProvider().add(spDatabase);

		response.setContentType("text/xml");

		writer.append(this.getRespond(catalog));
		writer.close();
	}

	/*************************************************************************
	 * PRIVATE
	 ************************************************************************/

	private boolean checkUser(final HttpServletRequest request, final HttpServletResponse responses) {
		final String authentification = request.getHeader("Authorization");

		if (authentification == null || authentification.isEmpty()
				|| !authentification.toUpperCase().startsWith("BASIC")) {
			responses.setHeader("WWW-Authenticate", "Not Authorized");
			try {
				responses.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		final String userpassEncoded = authentification.substring(6);
		final String userpassDecoded = new String(Base64.decodeBase64(userpassEncoded));

		// Check user and password are allowed
		// TODO check the password here
		System.out.println(userpassDecoded);
		return true;
	}

	private String getSchema() {
		final ServletContext ctx = this.getServletContext();
		final InputStream inputSchema = ctx.getResourceAsStream(PATH_TYPES_SCHEMA);
		final String schema = Utilities.getString(inputSchema); // TODO do later
		return schema;
	}

	private String getRespond(final ServiceProviderCatalog catalog) {
		final JAXBEngine engine = JAXBEngine.getInstance();
		final ByteOutputStream out = new ByteOutputStream();
		engine.write(catalog, out, ServiceProviderObjectFactory.class);
		final String strRespond = out.toString();
		out.close();
		return strRespond;
	}

	private String getBaseUrl(final HttpServletRequest request) {
		return request.getScheme()
				+ "://" + request.getServerName()
				+ ":" + request.getServerPort()
				+ request.getContextPath();
	}

}
