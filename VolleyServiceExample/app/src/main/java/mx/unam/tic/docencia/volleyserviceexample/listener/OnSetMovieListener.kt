package mx.unam.tic.docencia.webserviceexample.listener

import mx.unam.tic.docencia.webserviceexample.models.Movie

interface OnSetMovieListener {

    fun onSetMovie(movie:Movie)
}