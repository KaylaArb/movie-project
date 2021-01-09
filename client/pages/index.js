import Head from 'next/head'
import Link from "next/link";
import styles from '../styles/Home.module.css'
import Footer from "../components/Footer";
import MovieBlock from "../components/MovieBlock";
import ReactPaginate from "react-paginate"
import router from "next/router"
import {useState} from 'react';
import fetch from "node-fetch"

export default function Home({shows, show}) {
    const [searchValue, setSearchValue] = useState('');
    const [homePage, setHomePage] = useState(true);
    const [forcePage, setForcePage] = useState(null);
    let filters = [{name :"Choose a Filter...", value: null }, {name :"Top Rated", value:"high_rating" },{name :"Lowest Rated", value:"low_rating"}, {name:"Popularity", value:"popularity"} ]
    let filterButtons = [{name :"Ascending", value:"ascending" }, {name :"Descending", value:"descending" }]

    const handlePagination = page => {
        const path = router.pathname
        const query = router.query
        query.page = page.selected + 1
        if(query.page > 1) {
            setHomePage(false)
            router.push({
                pathname: path,
                query: query,
            }).then(() => window.scrollTo(0, 0));

        } else {
            setHomePage(true)
            router.push({
                    pathname: path}
            ).then(() => window.scrollTo(0, 0));
        }
    }

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            handleSearch();
        }
    }

    const handleChangeSearchValue = (e) => {
        if (e && e.target) {
            setSearchValue(e.target.value);
        }
    };

    const handleSearch = () => {
        const path = router.pathname
        const query = router.query
        query.search = searchValue
        setHomePage(false);
        router.push({
            pathname: path,
            query: query,
        }).then(() => window.scrollTo(0, 0));
        setSearchValue('')
    };

    const favourites = (value) => {
        const path = router.pathname
        const query = router.query
        query.favourites = value
        router.push({
            pathname: path,
            query: query,
        }).then(() => setHomePage(false));
    }

    return (
        <div className={styles.container}>
            <Head>
                <title>Howdy!</title>
            </Head>
            <main className={styles.main}>
                <div className={styles.filterContainer}>
                    <div className={styles.searchContainer}>
                        <input
                            type="text"
                            name="search"
                            placeholder="Search tv shows..."
                            className={styles.searchInput}
                            value={searchValue}
                            onKeyDown={handleKeyDown}
                            onChange={handleChangeSearchValue}/>
                        {
                            searchValue && (
                                <button
                                    className={styles.clearButton}
                                    onClick={() => setSearchValue('')}
                                />
                            )
                        }
                        <button
                            type="submit"
                            className={styles.formButton}
                            onClick={handleSearch}
                        />
                    </div>
                    <div>
                        <a onClick={() => favourites("favourites")} className={styles.favourites}>Kayla's Favourites</a>
                    </div>
                    <div>
                        {filterButtons.map((filter) => (
                            <button type="submit" value={filter.value} id="buttonFilter" onClick={() => buttonFilter(filter.value)} className={styles.filterButtons}>{filter.name === "Descending" ? <p>˅</p> : <p>˄</p>} </button>
                        ))}
                    </div>
                    <select id="filter" className={styles.selectInput} onChange={filter}>
                        {filters.map((filter) => (
                            <option value={filter.value} key={filter.value} >{filter.name}</option>
                        ))}
                    </select>
                </div>
                <div className={`${styles.backHome} ${!homePage ? styles.active : ''}`}>
                    <a onClick={() => handlePagination(1)}>Back to Home Page</a>
                </div>

                <div className={styles.movieGrid}>
                    {shows ? shows.results.map((show) => (
                        <MovieBlock key={show.id} movie={show}/>
                    )) : show.length < 1 ? <p className={styles.noResults}>No results found</p> : show.map((show) => (
                        <MovieBlock key={show.id} movie={show}/>))
                     }
                </div>
                {show ? '' : <ReactPaginate
                    marginPagesDisplayed={1}
                    previousLabel={"Previous"}
                    nextLabel={"Next"}
                    initialPage={shows.page - 1}
                    forcePage={shows.page - 1}
                    pageCount={shows.total_pages}
                    onPageChange={handlePagination}
                    containerClassName={styles.pagination}
                    activeClassName={styles.active}
                    previousClassName={styles.previous}
                    nextClassName={styles.next}
                    pageLinkClassName={styles.page}
                    disabledClassName={styles.disabled}
                /> }

            </main>
            <Footer/>
        </div>
    )
}

export async function getServerSideProps({query}) {
    const page = query.page || 1
    const search = query.search || null
    const favourites = query.favourites || null
    console.log("this is page number = " + page)
    console.log("this is search result = " + search)
    if(search === null && favourites === null) {
        const shows = await fetch(`http://localhost:8080/api/page=${page}`).then(r => r.json())
        return {props: {shows}}
    } else if(search) {
        const show = await fetch(`http://localhost:8080/api/search=${search}`).then(r => r.json())
        return {props: {show}}
    } else if(favourites) {
        const show = await fetch(`http://localhost:8080/api/filter/favourites`).then(r => r.json())
        return {props: {show}}
    }
}

function filter() {
    let value = document.getElementById("filter").value;
    fetch("http://localhost:8080/api/filter/" + value)
        .then((response) => response.json())
        .then( response => {
            router.reload(window.location.pathname);})
}

function buttonFilter(buttonValue) {
    let value = buttonValue;
    fetch("http://localhost:8080/api/filter/" + value)
        .then((response) => response.json())
        .then( response => {
            router.reload(window.location.pathname);})
}

