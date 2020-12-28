import Head from 'next/head'
import styles from '../styles/Home.module.css'
import axios from "axios";
import Footer from "../components/Footer";
import MovieBlock from "../components/MovieBlock";
import {router} from "next/client";
import {useState} from 'react';

export default function Home({movies}) {

    return (
        <div className={styles.container}>
            <Head>
                <title>Create Next App</title>
                <link rel="icon" href="/favicon.ico"/>
            </Head>
            <main className={styles.main}>
                <div className={styles.movieGrid}>
                    {movies.map((movie) => (
                        <MovieBlock movie={movie}/>
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

