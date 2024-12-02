# FILL IN ALL THE FUNCTIONS IN THIS TEMPLATE
# MAKE SURE YOU TEST YOUR FUNCTIONS WITH MULTIPLE TEST CASES
# ASIDE FROM THE SAMPLE FILES PROVIDED TO YOU, TEST ON YOUR OWN FILES

# WHEN DONE, SUBMIT THIS FILE TO CANVAS

from collections import defaultdict
from collections import Counter

# YOU MAY NOT CODE ANY OTHER IMPORTS

# ------ TASK 1: READING DATA  --------

# 1.1
def read_ratings_data(f):
    # parameter f: movie ratings file name f (e.g. "movieRatingSample.txt")
    # return: dictionary that maps movie to ratings
    # WRITE YOUR CODE BELOW

    # value is of type list
    moviesAndRatings = dict()

    with open(f, "r") as file:
        for line in file:
            movie, rating, _ = line.split('|')
            if movie not in moviesAndRatings:
                moviesAndRatings[movie] = []
            moviesAndRatings[movie].append(float(rating.strip()))

    return moviesAndRatings

# 1.2
def read_movie_genre(f):
    # parameter f: movies genre file name f (e.g. "genreMovieSample.txt")
    # return: dictionary that maps movie to genre
    # WRITE YOUR CODE BELOW

    moviesAndGenre = dict()

    with open(f, "r") as file:
        for line in file:
            genre, _, movie = line.split('|')
            moviesAndGenre[movie.strip()] = genre

    return moviesAndGenre

# ------ TASK 2: PROCESSING DATA --------

# 2.1
def create_genre_dict(d):
    # parameter d: dictionary that maps movie to genre
    # return: dictionary that maps genre to movies
    # WRITE YOUR CODE BELOW

    genreToMovie = dict()

    for movie, genre in d.items():
        if genre not in genreToMovie:
            genreToMovie[genre] = []
        genreToMovie[genre].append(movie)

    return genreToMovie


# 2.2
def calculate_average_rating(d):
    # parameter d: dictionary that maps movie to ratings
    # return: dictionary that maps movie to average rating
    # WRITE YOUR CODE BELOW

    averageMovieRating = dict()

    for movie, ratings in d.items():
        mean = sum(ratings) / len(ratings)
        averageMovieRating[movie] = mean

    return averageMovieRating

# ------ TASK 3: RECOMMENDATION --------

# 3.1
def get_popular_movies(d, n=10):
    # parameter d: dictionary that maps movie to average rating
    # parameter n: integer (for top n), default value 10
    # return: dictionary that maps movie to average rating,
    #         in ranked order from highest to lowest average rating
    # WRITE YOUR CODE BELOW

    topNMovies = dict(sorted(d.items(), key=lambda kv: kv[1], reverse=True))
    return dict(list(topNMovies.items())[:n])

# 3.2
def filter_movies(d, thres_rating=3):
    # parameter d: dictionary that maps movie to average rating
    # parameter thres_rating: threshold rating, default value 3
    # return: dictionary that maps movie to average rating
    # WRITE YOUR CODE BELOW

    # "return key: value for key, value in d if value > thres_rating"
    return {k: v for k, v in d.items() if v >= thres_rating}

# 3.3
def get_popular_in_genre(genre, genre_to_movies, movie_to_average_rating, n=5):
    # parameter genre: genre name (e.g. "Comedy")
    # parameter genre_to_movies: dictionary that maps genre to movies
    # parameter movie_to_average_rating: dictionary  that maps movie to average rating
    # parameter n: integer (for top n), default value 5
    # return: dictionary that maps movie to average rating
    # WRITE YOUR CODE BELOW

    # if genre or movie not exist
    if genre not in genre_to_movies.keys():
        return

    popularInGenre = {k: movie_to_average_rating[k] for k in genre_to_movies[genre]}
    return get_popular_movies(popularInGenre, n)

# 3.4
def get_genre_rating(genre, genre_to_movies, movie_to_average_rating):
    # parameter genre: genre name (e.g. "Comedy")
    # parameter genre_to_movies: dictionary that maps genre to movies
    # parameter movie_to_average_rating: dictionary  that maps movie to average rating
    # return: average rating of movies in genre
    # WRITE YOUR CODE BELOW

    # if genre not exist
    if genre not in genre_to_movies.keys():
        return

    totalSum = sum({movie: movie_to_average_rating[movie] for movie in genre_to_movies[genre]}.values())
    return totalSum / len(genre_to_movies[genre])

# 3.5
def genre_popularity(genre_to_movies, movie_to_average_rating, n=5):
    # parameter genre_to_movies: dictionary that maps genre to movies
    # parameter movie_to_average_rating: dictionary  that maps movie to average rating
    # parameter n: integer (for top n), default value 5
    # return: dictionary that maps genre to average rating
    # WRITE YOUR CODE BELOW
    genrePopularity = {genre: get_genre_rating(genre, genre_to_movies, movie_to_average_rating)
                       for genre in genre_to_movies.keys()}

    sortedGenrePopularity = dict(sorted(genrePopularity.items(), key=lambda item: item[1], reverse=True))
    return dict(list(sortedGenrePopularity.items())[:n])

# ------ TASK 4: USER FOCUSED  --------

# 4.1
def read_user_ratings(f):
    # parameter f: movie ratings file name (e.g. "movieRatingSample.txt")
    # return: dictionary that maps user to list of (movie,rating)
    # WRITE YOUR CODE BELOW

    userToMovieRating = dict()
    with open(f, "r") as f:
        for line in f:
            movie, rating, userID = line.strip().split('|')
            userID = int(userID)
            if userID not in userToMovieRating:
                userToMovieRating[userID] = [(movie, rating)]
            else:
                userToMovieRating[userID].append((movie, rating))
    return userToMovieRating

# 4.2
def get_user_genre(user_id, user_to_movies, movie_to_genre):
    # parameter user_id: user id
    # parameter user_to_movies: dictionary that maps user to movies and ratings
    # parameter movie_to_genre: dictionary that maps movie to genre
    # return: top genre that user likes
    # WRITE YOUR CODE BELOW

    # find movies the user watched
    genreWiseMovieRating = {}
    for movie, rating in user_to_movies[user_id]:
        genre = movie_to_genre[movie]
        rating = float(rating)
        if genre not in genreWiseMovieRating:
            genreWiseMovieRating[genre] = [rating]
        else:
            genreWiseMovieRating[genre].append(rating)

    # sort by average of ratings for each genre
    genreWiseMovieRating = {k: sum(v)/len(v) for k, v in genreWiseMovieRating.items()}
    sortedGenreWiseMovieRating = sorted(genreWiseMovieRating.items(), key=lambda kv: kv[1], reverse=True)

    return sortedGenreWiseMovieRating[0][0]

# 4.3
def recommend_movies(user_id, user_to_movies, movie_to_genre, movie_to_average_rating):
    # parameter user_id: user id
    # parameter user_to_movies: dictionary that maps user to movies and ratings
    # parameter movie_to_genre: dictionary that maps movie to genre
    # parameter movie_to_average_rating: dictionary that maps movie to average rating
    # return: dictionary that maps movie to average rating
    # WRITE YOUR CODE BELOW

    # find user's favorite genre
    topGenre = get_user_genre(user_id, user_to_movies, movie_to_genre)

    # find average rating of movies in user's favorite genre, do not consider already watched movies
    popularInGenre = {k: v for k, v in movie_to_average_rating.items() if movie_to_genre[k] == topGenre}
    for movie, rating in user_to_movies[user_id]:
        if movie in movie_to_genre:
            popularInGenre.pop(movie, None)
    # sort by average rating
    popularInGenre = dict(sorted(popularInGenre.items(), key=lambda x: x[1], reverse=True))
    # recommend top 3 movies in popularInGenre
    # if less than 3, return all (slicing handles this case by default)
    return dict(list(popularInGenre.items())[:3])

# -------- main function for your testing -----
def main():
    ratings = read_ratings_data("new_ratings.txt")
    genres = read_movie_genre("new_movies.txt")

    movie_genre_dict = create_genre_dict(genres)
    movie_ratings_avg_dict = calculate_average_rating(ratings)

    top_movie_rating = get_popular_movies(movie_ratings_avg_dict)
    threshold_movie_rating = filter_movies(movie_ratings_avg_dict)
    pop_genre = get_popular_in_genre("Comedy", movie_genre_dict, movie_ratings_avg_dict)
    avg_genre_rating = get_genre_rating("Comedy", movie_genre_dict, movie_ratings_avg_dict)
    genre_ratings = genre_popularity(movie_genre_dict, movie_ratings_avg_dict)

    read_ratings = read_user_ratings("new_ratings.txt")
    user_genre = get_user_genre(6, read_ratings, genres)
    user_recommended = recommend_movies(23, read_ratings, genres, movie_ratings_avg_dict)

    print("----------------Starting Dicts Here------------------")
    print("")
    print(ratings)
    print("")
    print(genres)
    print("-----------------------------------------------------")

    print("")

    print("----------------New Dicts Here------------------")
    print("")
    print(movie_genre_dict)
    print("")
    print(movie_ratings_avg_dict)
    print("------------------------------------------------")

    print("")

    print("----------------Special Dicts Here------------------")
    print("")
    print(top_movie_rating)
    print("")
    print(threshold_movie_rating)
    print("")
    print(pop_genre)
    print("")
    print(avg_genre_rating)
    print("")
    print(genre_ratings)
    print("----------------------------------------------------")

    print("")

    print("----------------User Dicts Here------------------")
    print("")
    print(read_ratings)
    print("")
    print(user_genre)
    print("")
    print(user_recommended)
    print("--------------------------------------------------")

# DO NOT write ANY CODE (including variable names) outside of any of the above functions
# In other words, ALL code your write (including variable names) MUST be inside one of
# the above functions

# program will start at the following main() function call
# when you execute hw1.py
main()
