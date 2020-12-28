import Head from 'next/head'
import styles from '../styles/Home.module.css'
import axios from "axios";
import Footer from "../components/Footer";
import MovieBlock from "../components/MovieBlock";


export default function Home({movies}) {

    return (
        <div className={styles.container}>
            <Head>
                <title>Create Next App</title>
                <link rel="icon" href="/favicon.ico"/>
            </Head>
            <main className={styles.main}>
                <div className={styles.searchContainer}>


                <input
                    type="text"
                    name="search"
                    placeholder="Search tv shows..."
                    className={styles.searchInput}
                    // value={searchValue}
                    // onKeyDown={handleKeyDown}
                    // onChange={handleChangeSearchValue}
                />
                {/*{*/}
                {/*    searchValue && (*/}
                {/*        <button*/}
                {/*            className={styles.clearButton}*/}
                {/*            onClick={() => setSearchValue('')} />*/}
                {/*    )*/}
                {/*}*/}
                <button
                    type="submit"
                    className={styles.formButton}
                    // onClick={handleSearch}
                />
                </div>
                <div className={styles.movieGrid}>
                    {movies.map((movie) => (
                        <MovieBlock key={movie.id} movie={movie}/>
                    ))}
                </div>
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

