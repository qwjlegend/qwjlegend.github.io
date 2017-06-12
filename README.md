## Welcome to My Project Exhibition

##### Table of Content

| Project | Link | Code |
|-|-|-|
| AutoComplete | [Visualization for AutoComplete](http://54.215.249.110/autocomplete)| [Github Link](https://github.com/qwjlegend/myBigDataProjects/tree/master/AutoComplete) |
| PageRank |[Visualization for PageRank](http://54.215.249.110/pagerank_search) | [Github Link](https://github.com/qwjlegend/myBigDataProjects/tree/master/PageRank) |
| Movie Recommendation | [Visualization for Movie Recommendation] |

### Project1: AutoComplete


###### Description

1. Constructed a N-Gram library based on Wiki data set, built a Language Model by computing the Gram probabilities to generate (frequency) data and stored them into MySql.
2. Fetch data from MySql using Jquery and PHP to realize a real time auto completion feature of a searching engine on the web browser.


### Project2: PageRank
###### Description

1. Implemented a page rank algorithm based on Twitter social network data sets with 11.3 million user profiles and 85 million social relations. 
2. Formulated the relations between different users using transition matrix, calculated each user's rank value through 30 iterations until converge using EMR cluster.
3. Visualized the social network Graph based on the resulting PageRank matrix through Node.js.


###### Link to Data source
```
http://socialcomputing.asu.edu/datasets/Twitter
```
### Project3: Movie Recommendation System
###### Description

1. Formulated a user rating matrix and a Co-concurrence matrix based on Netflix raw data set with 1 millions users and 1 million movies.
2. Merged the two matrices using a Object-based collaborative filtering algorithm to compute the movie recommendation list.
3. Realized the algorithm by connecting 4 separate Mapreduce jobs and deployed them on AWS EMR cluster.


