## My Projects

##### Table of Content

| Project | Link | Code |
|-|-|-|
| NYC Taxi | [Visualization for NYCTaxi](http://web.qwjlegend.com:5006/taxi) | Private|
| AutoComplete | [Visualization for AutoComplete](http://web.qwjlegend.com/autocomplete/index.php)| [Github Link](https://github.com/qwjlegend/qwjlegend.github.io/tree/master/AutoComplete) |
| PageRank |[Visualization for PageRank](http://web.qwjlegend.com/pagerank_search) | [Github Link](https://github.com/qwjlegend/qwjlegend.github.io/tree/master/PageRank) |
| Movie Recommendation |  | [Github Link](https://github.com/qwjlegend/qwjlegend.github.io/tree/master/RecommenderSystem)|


### Project1: NYC Taxi

###### Description

1. Cleaned and filtered 1.2 Billion NYC Taxi Trip Data (300GB) and stored them in S3 buckets.
2. Built and configured data pipeline based on AWS resources automatically byTerraform and Ansible.
3. Designed a MapReduce program and deployed it on an auto scaling group of EC2 instances to generate the statistics of the taxi trip data.
4. Scheduled and tracked tasks on each node by SQS, where each task read the input by date range.
5. Built a Docker image of the MapReduce program and pushed it to ECS repository as an alternative to facilitate the scaling out process and compared the latencies.
6. Aggregated and stored the output of reducer into DynamoDB and used it as the data source for Bokeh visualization.


###### Link to Data source
```
http://www.nyc.gov/html/tlc/html/about/trip_record_data.shtml
```


### Project2: AutoComplete


###### Description

1. Constructed a N-Gram library based on Wiki data set, built a Language Model by computing the Gram probabilities to generate (frequency) data and stored them into MySql.
2. Fetch data from MySql using Jquery and PHP to realize a real time auto completion feature of a searching engine on the web browser.




### Project3: PageRank
###### Description

1. Implemented a page rank algorithm based on Twitter social network data sets with 11.3 million user profiles and 85 million social relations. 
2. Formulated the relations between different users using transition matrix, calculated each user's rank value through 30 iterations until converge using EMR cluster.
3. Visualized the social network Graph based on the resulting PageRank matrix through Node.js.


###### Link to Data source
```
http://socialcomputing.asu.edu/datasets/Twitter
```

### Project4: Movie Recommendation System
###### Description

1. Formulated a user rating matrix and a Co-concurrence matrix based on Netflix raw data set with 480k users, 17k movies and over 100 million ratings.
2. Merged the two matrices using a Item-based collaborative filtering algorithm to compute the movie recommendation list and deployed the jobs on AWS Hadoop cluster.

###### Link to Data source
```
http://academictorrents.com/details/9b13183dc4d60676b773c9e2cd6de5e5542cee9a
```


