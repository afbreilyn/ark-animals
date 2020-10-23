import React, { FormEvent, useState } from 'react'
import './App.css'
import axios from 'axios'

export const App = () => {
  const [greeting, setGreeting] = useState("")

  const mySubmit = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault()
    const els = e.currentTarget.elements
    axios.post('/api/mustachios', {
      firstName: (els.namedItem('sticky-text') as HTMLInputElement).value
    })
      .then((response) => {
        setGreeting("hello from the backend: " + response.data.firstName)
      })
  }

  return (
    <div className="App">
      <h1>cards!</h1>
      <form data-testid="new-card-form" onSubmit={e => mySubmit(e)}>
        <label htmlFor="sticky-text">Sticky note text</label>
        <input
          id="sticky-text"
          name="sticky-text"
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
