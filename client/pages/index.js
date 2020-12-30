import Head from 'next/head'
import styles from '../styles/Home.module.css'
import Footer from "../components/Footer";
import MovieBlock from "../components/MovieBlock";
import ReactPaginate from "react-paginate"
import {useRouter} from "next/router"
import fetch from "node-fetch"

export default function Home({movies}) {
    const filters = ["Top Rated", "Lowest Rated","Kayla's Recommended", "Descending","Ascending"]
    const router = useRouter()

    const handlePagination = page => {
        const path = router.pathname
        const query = router.query
        query.page = page.selected + 1
        router.push({
            pathname: path,
            query: query,
        }).then(() => window.scrollTo(0, 0));
    }

    return (
        <div className={styles.container}>
            <Head>
                <title>Create Next App</title>
                <link rel="icon" href="/favicon.ico"/>
            </Head>
            <main className={styles.main}>
                <div className={styles.filterContainer}>
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
                    <select className={styles.selectInput}>
                        {filters.map((filter) => (
                            <option value={filter} key={filter}>{filter}</option>
                        ))}
                    </select>

                </div>
                <div className={styles.movieGrid}>
                    {movies.results.map((movie) => (
                        <MovieBlock key={movie.id} movie={movie}/>
                    ))}
                </div>
                <ReactPaginate
                    marginPagesDisplayed={1}
                    previousLabel={"Previous"}
                    nextLabel={"Next"}
                    // breakLabel={"..."}
                    initialPage={movies.page - 1}
                    pageCount={movies.total_pages}
                    onPageChange={handlePagination}
                    containerClassName={styles.pagination}
                    activeClassName={styles.active}
                    previousClassName={styles.previous}
                    nextClassName={styles.next}
                    pageLinkClassName={styles.page}
                    disabledClassName={styles.disabled}
                />
            </main>
            <Footer/>
        </div>
    )
}

export async function getServerSideProps({query}) {
    const page = query.page || 1
    const res = await fetch(`http://localhost:8080/api/page=${page}`)
    const movies = await res.json()
    return {props: {movies}}
}
