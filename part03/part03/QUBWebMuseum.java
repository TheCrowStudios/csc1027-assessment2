package part03;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import web.Response;
import web.WebInterface;
import web.WebRequest;

public class QUBWebMuseum {
	static Museum museum = new Museum();

    public static void main(String[] args) {
        museum = Helper.generateMuseum();
        final String ROOT = "/home/crowie/eclipse-workspace/Assessment2/src/part03/part03/";
        WebInterface wInterface = new WebInterface(8888);
        ArrayList<WebRequest> wqueue = new ArrayList<WebRequest>();
        WebInterface.launchBrowser("http://localhost:8888/index.html");

        while (true) {
            wInterface.getQueue(wqueue);

            for (int i = 0; i < wqueue.size(); i++) {
                WebRequest wr = wqueue.get(i);
                System.out.println("Web request to: " + wr.path);

                String filePath = ROOT + "public/" + wr.path;
                if (wr.path.equals("artifacts.json")) {
                    wr.r = new Response();
                } else {
                    System.out.println("Attempting to get file: " + filePath);
                    File file = new File(filePath);
                    if (file.exists()) {
                        try {
                            long maxlen = file.length();
                            FileInputStream fis = new FileInputStream(file);

                            // This range and offset code enables a webserver to request part of a file,
                            // this supports applications like streaming video
                            long startFrom = 0;
                            long endat = -1;
                            long rangebytes = -1;
                            String resulttype = WebRequest.HTTP_OK;
                            String range = wr.parms.get("Range");
                            String rangeend = null;
                            if (range != null) {
                                System.out.println(range);
                                if (range.startsWith("bytes=")) {
                                    resulttype = WebRequest.HTTP_PARTIAL;
                                    range = range.substring("bytes=".length());
                                    int minus = range.indexOf('-');
                                    if (minus > 0) {
                                        rangeend = range.substring(minus + 1);
                                        range = range.substring(0, minus);
                                    }
                                    try {
                                        startFrom = Long.parseLong(range);
                                        endat = Long.parseLong(rangeend);
                                    } catch (NumberFormatException nfe) {
                                        rangebytes = 512 * 1024;
                                        if (rangebytes > (maxlen - startFrom)) {
                                            resulttype = WebRequest.HTTP_OK;
                                            rangebytes = (maxlen - startFrom);
                                        } else {
                                            resulttype = WebRequest.HTTP_PARTIAL;
                                        }
                                    }

                                }
                                if (endat != -1) {
                                    rangebytes = endat - startFrom;
                                } else {
                                    rangebytes = (maxlen - startFrom);
                                }
                            } else {
                                long chunk = 100 * 1024 * 1024;
                                rangebytes = maxlen - startFrom;
                                if (rangebytes > chunk) {
                                    rangebytes = chunk;
                                    resulttype = WebRequest.HTTP_PARTIAL;
                                }
                            }

                            try {
                                fis.skip(startFrom);
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }

                            String mime = WebRequest.getMimeType(wr.path);

                            wr.r = new Response(resulttype, mime, rangebytes, fis);
                            wr.r.addHeader("Content-length", "" + (rangebytes));
                            wr.r.addHeader("Content-range", "bytes " + startFrom + "-" +
                                    (startFrom + rangebytes - 1) + "/" + maxlen);
                        } catch (Exception e) {
                            wr.r = new Response(WebRequest.HTTP_NOTFOUND, WebRequest.MIME_HTML, e.getMessage());
                        }
                    } else {
                        wr.r = new Response(WebRequest.HTTP_NOTFOUND, WebRequest.MIME_HTML, "File not found");
                    }
                }
            
                Thread thread = new Thread(wr);
                thread.setDaemon(true);
                thread.start();
            }

            try {
                wqueue.clear();
                Thread.sleep(10);
                
            } catch (Exception e) {
            }
        }
    }
}
