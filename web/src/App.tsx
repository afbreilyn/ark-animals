import React, { FormEvent, useEffect, useState } from 'react'
import logo from './logo.svg'
import './App.css'
import axios, { AxiosResponse } from 'axios'


export const App = () => {
  const [greeting, setGreeting] = useState("")

  const mySubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault()
    axios.get('/api/someRandomEndpoint')
      .then((response: AxiosResponse<string>) => {
        setGreeting(response.data)
      })
  }

  return (
    <div className="App">
      <h1>cards!</h1>
      <form data-testid="new-card-form" onSubmit={e => mySubmit(e)}>
        <label htmlFor="sticky-text">Sticky note text</label>
        <input
          id="sticky-text"
          data-testid="sticky-text-input"
        />
        <button type="submit" data-testid="submit-button">Submit</button>
      </form>
      <h1>
        {greeting}
      </h1>
    </div>
  )
}

export default App
