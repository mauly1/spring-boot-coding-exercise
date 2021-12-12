package com.telstra.codechallenge.starrepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.platform.runner.JUnitPlatform;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GithubClientControllerTest {

    @InjectMocks
    GithubClientController githubClientController;
    @Mock
    GithubClientService githubClientService;

    @Test
    public void tetGetHottestResponseIsNotNull() {
        final int NO_OF_DAYS = 7;
        final int NO_OF_REPOS = 3;
        RepositoryDetail repositoryDetail = new RepositoryDetail();
        repositoryDetail.setName("ja-netfilter");
        repositoryDetail.setHtmlUrl("https://github.com/ja-netfilter/ja-netfilter");
        repositoryDetail.setWatchersCount("1234");
        repositoryDetail.setLanguage("java");
        RepositoryDetail repositoryDetail2 = new RepositoryDetail();
        repositoryDetail2.setName("ja-netfilter-2 response");
        repositoryDetail2.setHtmlUrl("https://github.com/ja-netfilter/ja-Test");
        repositoryDetail2.setWatchersCount("4567");
        repositoryDetail2.setLanguage("java -2 response");

        RepositoryResponse repositoryResponse = new RepositoryResponse();
        List<RepositoryDetail> repositoryDetailList = new ArrayList<>();
        repositoryDetailList.add(repositoryDetail);
        repositoryDetailList.add(repositoryDetail2);

        repositoryResponse.setItems(repositoryDetailList);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(githubClientService.getHottestRepos(NO_OF_REPOS, NO_OF_DAYS)).thenReturn(repositoryResponse);

        // when
        ResponseEntity<RepositoryResponse> result = githubClientController.getHottestRepos(NO_OF_REPOS);

        // then
        assertThat(result.getStatusCode().OK);
        assertThat(result.getBody().getItems().size()).isEqualTo(2);

        assertThat(result.getBody().getItems().get(0).getName())
                .isEqualTo("ja-netfilter");

        assertThat(result.getBody().getItems().get(0).getLanguage())
                .isEqualTo("java");
    }

    @Test
    public void tetGetHottestResponseISNull() {
        final int NO_OF_DAYS = 7;
        final int NO_OF_REPOS = 3;
        RepositoryResponse repositoryResponse = new RepositoryResponse();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(githubClientService.getHottestRepos(NO_OF_REPOS, NO_OF_DAYS)).thenReturn(repositoryResponse);
        // when
        ResponseEntity<RepositoryResponse> result = githubClientController.getHottestRepos(NO_OF_REPOS);
        // then
        assertThat(result.getStatusCode().OK);
        assertThat(result.getBody().getItems().size()).isEqualTo(0);

    }

}