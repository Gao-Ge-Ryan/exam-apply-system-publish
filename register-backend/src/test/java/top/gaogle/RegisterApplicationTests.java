package top.gaogle;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.minio.errors.*;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import top.gaogle.framework.util.MinioUtil;
import top.gaogle.service.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RegisterApplicationTests {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${minio.bucket-name-public}")
    private String bucketName;

//    @Autowired
//    RegisterPublishAnnouncementTask registerPublishAnnouncementTask;
    @Autowired
    FileService fileService;
    @Autowired
    MinioUtil minioUtil;

    @Test
    void testTy(){

        System.out.println(1-(-1));

    }

    public  boolean isSSHPortOpen(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, timeout);
            return true; // 连接成功，端口开放
        } catch (IOException e) {
            log.error("ssss:",e);
            return false; // 连接失败，端口关闭
        }
    }

    @Test
    void testio(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("ge");
        strings.add("gaogeryantytyryan");
        String s = strings.get(1);
        String replace = s.replace("ryan", "jjjj").replace("ge","oo");
        System.out.println(strings);
        System.out.println("/usr/bin/tini");
        System.out.println("/usr/sbin/sshd ; echo 'root:g8UkDNRjufV!a%IDfBqLxfGq4hG#9E%#' | chpasswd && mkdir -p /root/.ssh && echo ${PUBLIC_KEY} > /root/.ssh/id_rsa.pub && echo ${PUBLIC_KEY} > /root/.ssh/authorized_keys && echo \"-----BEGIN RSA PRIVATE KEY-----\" > /root/.ssh/id_rsa && echo ${PRIVATE_KEY} | sed \"s| |\\n|g\" >> /root/.ssh/id_rsa && echo \"-----END RSA PRIVATE KEY-----\" >> /root/.ssh/id_rsa && chmod 700 /root/.ssh/id_rsa ;mkdir -p /usr/local/lib/inais && echo ${VCUDA_PORT} > /usr/local/lib/inais/.vcuda_config && echo ${LOCAL_HOST_IP} >> /usr/local/lib/inais/.vcuda_config; echo \"cd /zhengyx\" >> ~/.bashrc && source ~/.bashrc ;mkdir -p /root/.jupyter && echo '{\"NotebookApp\":{\"token\": \"49369\"}}' > /root/.jupyter/jupyter_notebook_config.json && jupyter lab --allow-root --notebook-dir=/zhengyx --LabApp.base_url=jupyter/https%3A%2F%2F10.30.121.100%3A49369 ||tail -f /dev/null");
        System.out.println("/usr/sbin/sshd ; echo 'root:g8UkDNRjufV!a%IDfBqLxfGq4hG#9E%#' | chpasswd && mkdir -p /root/.ssh && echo ${PUBLIC_KEY} > /root/.ssh/id_rsa.pub && echo ${PUBLIC_KEY} > /root/.ssh/authorized_keys && echo \"-----BEGIN RSA PRIVATE KEY-----\" > /root/.ssh/id_rsa && echo ${PRIVATE_KEY} | sed \"s| |\\n|g\" >> /root/.ssh/id_rsa && echo \"-----END RSA PRIVATE KEY-----\" >> /root/.ssh/id_rsa && chmod 700 /root/.ssh/id_rsa && echo \"cd /zhengyx\" >> ~/.bashrc && source ~/.bashrc ;mkdir -p /root/.jupyter && echo '{\"NotebookApp\":{\"token\": \"49369\"}}' > /root/.jupyter/jupyter_notebook_config.json && jupyter lab --allow-root --notebook-dir=/zhengyx --LabApp.base_url=jupyter/https%3A%2F%2F10.30.121.100%3A49369 ||tail -f /dev/null");


        System.out.println("/usr/sbin/sshd ; echo 'root:g8UkDNRjufV!a%IDfBqLxfGq4hG#9E%#' | chpasswd && mkdir -p /root/.ssh && echo ${PUBLIC_KEY} > /root/.ssh/id_rsa.pub && echo ${PUBLIC_KEY} > /root/.ssh/authorized_keys && echo \"-----BEGIN RSA PRIVATE KEY-----\" > /root/.ssh/id_rsa && echo ${PRIVATE_KEY} | sed \"s| |\\n|g\" >> /root/.ssh/id_rsa && echo \"-----END RSA PRIVATE KEY-----\" >> /root/.ssh/id_rsa && chmod 700 /root/.ssh/id_rsa && echo \"cd /zhengyx\" >> ~/.bashrc && source ~/.bashrc ;mkdir -p /root/.jupyter && echo '{\"NotebookApp\":{\"token\": \"49369\"}}' > /root/.jupyter/jupyter_notebook_config.json && jupyter lab --allow-root --notebook-dir=/zhengyx --LabApp.base_url=jupyter/https%3A%2F%2F10.30.121.100%3A49369 ||tail -f /dev/null");
    }

    @Test
    void testui() throws UnsupportedEncodingException {
        String decodedBase64Url = URLDecoder.decode("aHR0cDovLzgyLjE1Ny40Mi4yNTo5MDAxL3N0eWxlLXJlZ2lzdGVyLXB1YmxpYy9waWN0dXJlLzE4MTQ4NjYwMzIxOTY3MTg1OTIucG5n", StandardCharsets.UTF_8.toString());

        // Base64 解码
        String originalUrl = new String(Base64.getDecoder().decode(decodedBase64Url), StandardCharsets.UTF_8);

        // 打开 URL
        System.out.println(originalUrl);
    }

    @Test
    void testGaoge() throws IOException {
        // 要预览文件的访问地址
        String url = "https://linux-cli.download.hpc.jengcloud.com/linux-cli/latest/jchpc-cli";

        // Base64 编码
        String base64Url = Base64.getEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));

        // URL 编码
        String previewUrl = "http://172.17.18.200:8012/onlinePreview?url=" + URLEncoder.encode(base64Url, StandardCharsets.UTF_8.toString())+"&forceUpdatedCache=true";

        // 打开 URL
        System.out.println(previewUrl);
    }

    @Test
    void minio() throws IOException, InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            // Loki API 地址
            String baseUrl = "http://192.168.145.100:31430/loki/api/v1/query_range";

            // 查询参数
            String query = "{job=\"ingress-nginx/ingress-nginx\"}";

            // 获取当前时间和一小时前的时间（UNIX 时间戳，秒）
            long now = Instant.now().getEpochSecond(); // 当前时间
            long oneHourAgo = now - 3600; // 一小时前的时间

            // 将时间戳转换为纳秒（在秒后加9个0）
            String start = oneHourAgo + "000000000"; // 一小时前的时间戳（纳秒级）
            String end = now + "000000000"; // 当前时间戳（纳秒级）

            // URL 编码查询参数
            String urlParams = "query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) +
                    "&start=" + URLEncoder.encode(start, StandardCharsets.UTF_8.toString()) +
                    "&end=" + URLEncoder.encode(end, StandardCharsets.UTF_8.toString()) +
                    "&limit=100"; // 设置日志条目限制

            // 创建完整的 URL
            URL url = new URL(baseUrl + "?" + urlParams);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置请求头 (可根据需要添加)
            connection.setRequestProperty("Content-Type", "application/json");

            // 处理响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 成功
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // 关闭缓冲流
                in.close();

                // 输出结果
                System.out.println("Response: " + content.toString());
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            // 断开连接
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void contextLoads() throws IOException, InterruptedException {
        for (int i = 0; i <100; i++) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://console.hpc.jengcloud.com/ehpc-api/service-queue-provider/v1/queue/person?uid=2844379125474222")
                    .method("GET", null)
                    .addHeader("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyODQ0Mzc5MTI1NDc0MjIyIiwibGRhcFVpZCI6IjEwMTA2MiIsImxkYXBHaWQiOiIxMDEwNjIiLCJleHAiOjE3Mjg1OTE3ODcsImFjY291bnQiOiJ6aG91eXEifQ.xXBRn4yj0RyYqlH47E1G0to6zNQF85Ow6SJ4ZMfu4tw")
                    .addHeader("priority", "u=1, i")
                    .addHeader("projectid", "5726455318996758")
                    .addHeader("regionid", "1261481747917405")
                    .addHeader("Cookie", "vue_admin_template_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyODQ0Mzc5MTI1NDc0MjIyIiwibGRhcFVpZCI6IjEwMTA2MiIsImxkYXBHaWQiOiIxMDEwNjIiLCJleHAiOjE3Mjg1OTE3ODcsImFjY291bnQiOiJ6aG91eXEifQ.xXBRn4yj0RyYqlH47E1G0to6zNQF85Ow6SJ4ZMfu4tw")
                    .addHeader("X-Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2OTE2NTI0NTcsImV4cCI6MTY5MjI1NzI1NywiY29tcGFueUlkIjoiMzUzIiwiY29tcGFueVNuIjoiMDY2MjI2MTRmMTNhNGRlMTkyOGUzYWNhOWUwNjY4ZjIiLCJkZXBhcnRtZW50SWQiOiIxMzQzIiwidXNlck5hbWUiOiJ0ZXN0MDgyMTA1QHFkdW0uY29tIiwiaWQiOiI2NDgiLCJhdXRoIjoi5YWo6YOo5p2D6ZmQLeWLv-WKqCIsImlzTWFzdGVyIjoiMSJ9.QU7aOx31Z3iyeVjMoAf7Jak5unjPTwPfuhSV5TzszDTpw0v7W17ysKievMpHP8doipMAgyXFeg3jecVtszAU_g")
                    .addHeader("Accept-Language", "zh-CN")
                    .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .addHeader("Accept", "*/*")
                    .addHeader("Host", "console.hpc.jengcloud.com")
                    .addHeader("Connection", "keep-alive")
                    .build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            System.out.println("======================");
            TimeUnit.SECONDS.sleep(2);
        }

    }

    @Test
    void contextJTW() throws IOException, InterruptedException {
        String zhouyq = getToken("zhouyq");
        System.out.println(zhouyq);

    }
    public static String getToken(String username) {
        byte[] bytes = "admin321\n".getBytes();
        Algorithm algorithm = Algorithm.HMAC256(bytes);
        return JWT.create()
                .withClaim("iat", new Date(System.currentTimeMillis() - 10000))
                .withClaim("exp", new Date(System.currentTimeMillis() + 100000))
                .withClaim("sun", username)
                .sign(algorithm);
    }

}
