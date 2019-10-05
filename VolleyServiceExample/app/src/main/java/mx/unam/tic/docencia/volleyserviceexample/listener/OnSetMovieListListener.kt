package mx.unam.tic.docencia.webserviceexample.listener

import mx.unam.tic.docencia.webserviceexample.models.MoviesList

interface OnSetMovieListListener {

    fun onSetMovieList(moviesList: MoviesList)

}