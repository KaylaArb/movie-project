import styles from '../styles/Footer.module.css'

export default function Footer() {
    return (
        <footer className={styles.footer}>
            <div className={styles.content}>
                <p>Developed by Kayla Arbez</p>
                <img src='/icon.svg' className={styles.logo}/>
            </div>
        </footer>
    )
}


