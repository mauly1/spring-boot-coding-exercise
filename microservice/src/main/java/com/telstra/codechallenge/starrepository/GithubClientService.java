package com.telstra.codechallenge.starrepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class GithubClientService {
    public static final String SORT_KEY = "sort";
    public static final String QUERY = "q";
    public static final String ORDER_KEY = "order";
    public static final String PER_PAGE_LIMIT = "per_page";
    public static final String DESC = "desc";
    public static final String STARS = "stars";
    public static final String CREATED_DATE = "created:";

    private final RestTemplate restTemplate;

    @Autowired
    public GithubClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Value("${repository.base.url}")
    private String gitBaseUrl;


    public void setGitBaseUrl(String baseUrl) {
        this.gitBaseUrl = baseUrl;
    }

    public RepositoryResponse getHottestRepos(int noOfRepos, int lastDays) {
        log.debug("for getHottestRepos of service noOfRepos " + noOfRepos + " lastDays " + lastDays);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = getUriComponentsBuilder(gitBaseUrl, noOfRepos, lastDays);
        RepositoryResponse repositoryResponse = this.restTemplate.exchange(
                builder.build().toString(), HttpMethod.GET, new HttpEntity<>(headers),
                RepositoryResponse.class).getBody();
        log.debug("Client Service response body " + repositoryResponse.toString());
        return repositoryResponse;
    }

    public UriComponentsBuilder getUriComponentsBuilder(String url, int noOfRepos, int lastDays) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(QUERY, CREATED_DATE + RepositoryUtil.getYYYYMMDD(RepositoryUtil.getLastLocalDate(lastDays)))
                .queryParam(SORT_KEY, STARS)
                .queryParam(ORDER_KEY, DESC)
                .queryParam(PER_PAGE_LIMIT, noOfRepos);
    }


}
