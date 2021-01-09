import styles from '../styles/Footer.module.css'

export default function Footer() {
    return (
        <footer className={styles.footer}>
            <div className={styles.content}>
                <p>API source from The MoveDB</p>
                <a href="https://www.themoviedb.org/"><img src='/TMDB_logo.svg' className={styles.logo}/></a>
                <p>Developed by Kayla Arbez</p>
            </div>
        </footer>
    )
}


