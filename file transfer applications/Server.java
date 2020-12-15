import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class <code>Server</code> represents a simple file transfer server to
 * the clients.
 */
public class Server {
	private static final int DEFAULT_PORT = 8888;			// default port number
	private static final int DEFAULT_THREADS = 10;			// default number of threads
	private static final boolean DEBUG_MODE = true;		// enable or disable debug mode
	private static final String LOGGING_FILE = "log.txt";	// logging file name
	private static final String SERVER_RES_FOLDER = "serverFiles";	// server resource folder
	public static final int SUCCEED = 200;
	public static final int FAIL = 404;
	private ServerSocket socket;					// server socket
	private ExecutorService service;				// executor to manage thread pool

	/**
	 * Init the server.
	 */
	public Server() {
		try {
			socket = new ServerSocket(DEFAULT_PORT);
			service = Executors.newFixedThreadPool(DEFAULT_THREADS);
			System.out.println("Start Server @Port:" + DEFAULT_PORT);
		} catch (IOException e) {
			// e.printStackTrace();
			// in case the port is in use
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Starts the server.
	 */
	public void start() {
		while (true) {
			try {
				Socket connection = socket.accept();
				service.submit(new ClientHandler(connection));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// this class represents a handler for each client
	private static class ClientHandler implements Runnable {
		private Socket connection;	// connection socket to the client

		/**
		 * Init the client handler.
		 * @param connection connection socket to the client
		 */
		public ClientHandler(Socket connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				// wrapper the output and input stream
				PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String input;
				if ((input = br.readLine()) != null) {
					logging(input);
					// split the input line
					String[] commands = input.split("\\s+");
					if (commands.length == 1 && "LIST".equalsIgnoreCase(commands[0])) {
						pw.println(SUCCEED);
						for (String filename : getFileNames()) {
							pw.println(filename);
						}
					} else if (commands.length == 2) {
						String fileName = commands[1];
						if ("GET".equalsIgnoreCase(commands[0])) {
							File file = new File(SERVER_RES_FOLDER, fileName);
							if (!file.exists()) {
								pw.println(FAIL);
							} else {
								String line;
								pw.println(SUCCEED);
								BufferedReader fr = new BufferedReader(new FileReader(file));
								while ((line = fr.readLine()) != null) {
									pw.println(line);
								}
								fr.close();
							}
						} else {
							File file = new File(SERVER_RES_FOLDER, fileName);
							InputStream is = connection.getInputStream();
							FileOutputStream fos = new FileOutputStream(file);
							int size;
							byte[] buffer = new byte[1024];
							while ((size = is.read(buffer)) != -1) {
								fos.write(buffer, 0, size);
							}
							fos.close();
							System.out.println(size);
							is.close();

						}
					} else {
						// status code
						pw.println(FAIL);
						pw.write("Unknown command");
					}
				}
				br.close();
				pw.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// logging message to the default file
		private void logging(String message) {
			if (!DEBUG_MODE) {
				System.out.println("Debug mode is off.");
				return;
			}
			try {
				// append to the file
				BufferedWriter bw = new BufferedWriter(new FileWriter(LOGGING_FILE, true));
				bw.write(message);
				Date date = new Date();
				bw.write(new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss").format(date) + ":"
						+ connection.getInetAddress() + ":" + message + "\n");

				// close the stream
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// check the file name under the resource folder
		private String[] getFileNames() {
			return new File(SERVER_RES_FOLDER).list();
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}
}



















