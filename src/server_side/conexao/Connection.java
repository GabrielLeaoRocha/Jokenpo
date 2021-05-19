//classe de conexao do protocolo TCP

package server_side.conexao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

    //metodo de envio de mensagem
    public static void send (Socket socket, String txt) throws IOException {
        OutputStream out = null;
        try{
            out = socket.getOutputStream();
            out.write(txt.getBytes());
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    //metodo de recebimento de mensagem
    public static String recive (Socket socket) throws IOException {
        String txt = "";
        int qntdBytes;
        byte [] msgBytes = new byte[100];
        InputStream in = null;
        try{
            in = socket.getInputStream();
            qntdBytes = in.read(msgBytes);

            if(qntdBytes > 0){ //confere se teve algum caracter de entrada
                txt = new String(msgBytes);
            }
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            in.close();
        }
        return txt;
    }

}
