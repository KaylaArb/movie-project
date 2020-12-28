import styles from '../styles/MovieBlock.module.css'

export default function MovieBlock({movie}) {
    return (
        <div className={styles.block}>
            <img src={"https://image.tmdb.org/t/p/w200/" + movie.poster_path} className={styles.posterImage}/>
            <div className={styles.movieTextContainer}>
                <h3 className={styles.movieTitle}>{movie.name}</h3>
                <p className={styles.movieDate}>Premiere Date : {movie.first_air_date}</p>
                <p>Rating: {movie.vote_average}</p>
            </div>
        </div>
    )
}


