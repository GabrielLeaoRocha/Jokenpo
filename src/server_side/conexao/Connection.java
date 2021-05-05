//classe de conexao do protocolo TCP

package server_side.conexao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

    //metodo de envio de mensagem
    public static void send (Socket socket, String txt){
        OutputStream out;
        try{
            out = socket.getOutputStream();
            out.write(txt.getBytes());
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    //metodo de recebimento de mensagem
    public static String recive (Socket socket){
        String txt = "";
        int qntdBytes;
        byte [] msgBytes = new byte[22];
        InputStream in;
        try{
            in = socket.getInputStream();
            qntdBytes = in.read(msgBytes);

            if(qntdBytes > 0){ //confere se teve algum caracter de entrada
                txt = new String(msgBytes);
            }
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage());
        }
        return txt;
    }

}
