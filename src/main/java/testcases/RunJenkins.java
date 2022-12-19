package testcases;

public class RunJenkins {
   /* public static void main(String [] args) throws IOException {
       // http://user:gNouIkl2ca1t@
        String url ="https://jenkins.pal.net.vn/login?from=%2Fview%2FQA%2520Automation%2Fjob%2FQA-Automation%2Fview%2FNESS_AQS%2Fjob%2FTestRail_Test%2Fbuild%3Ftoken%3Disabella_P%40l332211";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        System.out.println(con.getResponseCode());
        con.disconnect();
    }
    public String scrape(String urlString, String username, String password)
            throws ClientProtocolException, IOException {
        URI uri = URI.create(urlString);
        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
                new UsernamePasswordCredentials(username, password));
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        CloseableHttpClient httpClient =
                HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        HttpGet httpGet = new HttpGet(uri);
        // Add AuthCache to the execution context
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setAuthCache(authCache);

        HttpResponse response = httpClient.execute(host, httpGet, localContext);

        return EntityUtils.toString(response.getEntity());
    }*/

}
