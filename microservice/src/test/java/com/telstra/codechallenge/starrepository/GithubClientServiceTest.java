package com.telstra.codechallenge.starrepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class GithubClientServiceTest
{
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    GithubClientService githubClientService;

    @Test
    public void testGetHottestRepos() throws URISyntaxException
    {
        final int NO_OF_REPO = 7;
        final int NO_OF_LAST=3;
        final String BASE_URL="https://api.github.com/search/repositories?q=created:2021-11-29&sort=stars&order=desc&per_page=1";

        RepositoryDetail repositoryDetail =new RepositoryDetail();
        repositoryDetail.setName("ja-netfilter");
        repositoryDetail.setHtmlUrl("https://github.com/ja-netfilter/ja-netfilter");
        repositoryDetail.setWatchersCount("1234");
        repositoryDetail.setLanguage("java");
        RepositoryDetail repositoryDetail2 =new RepositoryDetail();
        repositoryDetail2.setName("ja-netfilter-2 response");
        repositoryDetail2.setHtmlUrl("https://github.com/ja-netfilter/ja-Test");
        repositoryDetail2.setWatchersCount("4567");
        repositoryDetail2.setLanguage("java -2 response");

        RepositoryResponse repositoryResponse =new RepositoryResponse();
        List<RepositoryDetail> repositoryDetailList = new ArrayList<>();
        repositoryDetailList.add(repositoryDetail);
        repositoryDetailList.add(repositoryDetail2);

        repositoryResponse.setItems(repositoryDetailList);

        githubClientService.setGitBaseUrl(BASE_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
when(restTemplate.exchange(   githubClientService.getUriComponentsBuilder(BASE_URL,NO_OF_REPO,NO_OF_LAST).build().toString(), HttpMethod.GET, new HttpEntity<>(headers),RepositoryResponse.class)).thenReturn(new ResponseEntity<RepositoryResponse>(repositoryResponse, HttpStatus.OK));

        RepositoryResponse response= githubClientService.getHottestRepos(NO_OF_REPO,NO_OF_LAST);

        //Verify request succeed
        assertThat(response.getItems().size()).isEqualTo(2);

    }
}
