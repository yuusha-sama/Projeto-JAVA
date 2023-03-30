import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        
        
        // fazer uma conecção HTTP, e buscar os TOP 250 filmes
        
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json"; // usando endereço alternativo
        ExtratorDeConteudoDoIMDB extrator = new ExtratorDeConteudoDoIMDB();

        //String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/NASA-APOD.json";
        //ExtratorDeConteudoDaNasa extrator = new ExtratorDeConteudoDaNasa();

        //URI endereco = URI.create(url);
        //var client = HttpClient.newHttpClient(); 
        //var request = HttpRequest.newBuilder(endereco).GET().build();
        //HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        //String body = response.body() ;
        
        var http = new ClienteHttp();
        String json = http.buscaDados(url); 

        // extrair so os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeConteudo = parser.parse(json);
        

        // exibir dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradorDeFigurinhaJava();
        //var diretorio = new File("figurinha");
        //diretorio.mkdir();
        

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

           

            InputStream inputStream = new URL(conteudo.getUrlImage()).openStream();
            String nomeArquivo = "figurinha/" + conteudo.getTitulo() + ".png";
            
            geradora.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.getTitulo());
            System.out.println();
        }



    }
}
