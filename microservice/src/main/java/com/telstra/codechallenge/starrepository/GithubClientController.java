package com.telstra.codechallenge.starrepository;

import com.telstra.codechallenge.exceptions.InvalidRequestException;
import com.telstra.codechallenge.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/codechallenge")
public class GithubClientController {
    public static final int NO_OF_DAYS = 7;

    private GithubClientService githubClientService;

    public GithubClientController(GithubClientService githubClientService) {
        this.githubClientService = githubClientService;
    }

    @GetMapping("/hottest/{noOfRepos}")
    public ResponseEntity<RepositoryResponse> getHottestRepos(@PathVariable int noOfRepos) {
        log.debug("for hottest repository input value of noOfRepos is " + noOfRepos);
        if (noOfRepos == 0) {
            throw new InvalidRequestException("NO of Repos number should be greater then 0 !");
        }
        return ResponseEntity.ok(githubClientService.getHottestRepos(noOfRepos, NO_OF_DAYS));
    }

}

