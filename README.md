# HybridSearch

This project develops a concurrent filtering technique that speeds up hybrid search, which can be thought as a vector similarity search with additional scalar attribute filtering.

A brief introduction to Hybrid Search is available on https://milvus.io/docs/hybridsearch.md.
Traditionally, there have been 2 approaches to do hybrid search: pre-query filtering, which means entries are filtered before the index is built, and post-query filtering, which means entries are filtered after similarity search is complete. Both methods com with drawbacks. While pre-query filtering is inflexible against the change of filtering criteria which results in rebuilding the index, the number of results obtained from post-query filtering is not precise as the filtering outcome on similarity search results can only be estimated.

We propose a new technique, namely concurrent filtering, which aims at solving the above problems and improving computational performance at the same time. Instead of filtering before or after the vector search, we filter results during the search. This means that a full index is built as in post-query filtering, but the queries into the index contain information about the filtering criteria. If a point is invalid for the purpose of filtering, then the searching algorithm does not include it in the result list, which allows for larger flexibility in the remaining process. Implementations of concurrent filtering on VP Tree and NSW are provided. Experiment results indicate that it outperforms post-query filtering in both computation time and achieving exact number of results, and might be a better option than pre-query filtering when flexibility in the filtering criteria is needed.
