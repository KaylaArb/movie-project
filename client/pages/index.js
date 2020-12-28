import Head from 'next/head'
import styles from '../styles/Home.module.css'
import axios from "axios";
import Footer from "../components/Footer";

export default function Home({movies}) {
  return (
    <div className={styles.container}>
      <Head>
        <title>Create Next App</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main className={styles.main}>
        {movies.map((movie) => (
            <p>{movie.name}</p>
        ))}

      </main>
      <Footer/>
    </div>
  )
}
export async function getStaticProps() {
  const movies = await axios.get('http://localhost:8080/api/get').then(r => r.data)
  return {
    props: {movies},
  }
}

