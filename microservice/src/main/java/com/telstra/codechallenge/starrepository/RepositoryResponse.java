package com.telstra.codechallenge.starrepository;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RepositoryResponse {
    private List<RepositoryDetail> items = new ArrayList<>();
}
