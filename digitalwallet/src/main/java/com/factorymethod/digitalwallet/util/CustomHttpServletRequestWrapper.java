//package com.factorymethod.digitalwallet.util;
//
//
//import jakarta.servlet.ReadListener;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//
//import java.io.*;
//
//public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
//
//    private final String body;
//
//    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
//        super(request);
//        StringBuilder stringBuilder = new StringBuilder();
//        BufferedReader bufferedReader = null;
//        try {
//            InputStream inputStream = request.getInputStream();
//            if (inputStream != null) {
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                char[] charBuffer = new char[128];
//                int bytesRead;
//                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
//                    stringBuilder.append(charBuffer, 0, bytesRead);
//                }
//            }
//        } finally {
//            if (bufferedReader != null) {
//                bufferedReader.close();
//            }
//        }
//        body = stringBuilder.toString();
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
//        return new ServletInputStream() {
//            @Override
//            public boolean isFinished() {
//                return byteArrayInputStream.available() == 0;
//            }
//
//            @Override
//            public boolean isReady() {
//                return true;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//            }
//
//            @Override
//            public int read() throws IOException {
//                return byteArrayInputStream.read();
//            }
//        };
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }
//
//    public String getBody() {
//        return this.body;
//    }
//}
