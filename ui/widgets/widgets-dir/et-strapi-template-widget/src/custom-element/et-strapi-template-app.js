import ReactDOM from "react-dom"
import React from "react"
import App from '../App'
import { KEYCLOAK_EVENT_TYPE, subscribeToWidgetEvent } from "../helpers/widgetEvents"
const getKeycloakInstance = () =>
    (window && window.entando && window.entando.keycloak && { ...window.entando.keycloak, initialized: true }) || {
        initialized: false,
    }
class EtApp extends HTMLElement {
    constructor() {
        super();
        this.reactRootRef = React.createRef();
        this.mountPoint = null;
    }
    #config = {
        locale: 'en',
    }
    #updateConfig(value) {
        this.#config = JSON.parse(value)
    }
    static get observedAttributes() {
        return ["config"]
    }
    attributeChangedCallback(name, oldValue, newValue) {
        this.#updateConfig(newValue)
        ReactDOM.render(<App ref={this.reactRootRef} config={this.#config} />, this.mountPoint);
    }
    get config() {
        return this.reactRootRef.current ? this.reactRootRef.current.state : {};
    }
    set config(value) {
        return this.reactRootRef.current.setState(value);
    }
    connectedCallback() {
        this.mountPoint = document.createElement('span')
        this.keycloak = { ...getKeycloakInstance(), initialized: true }
        this.unsubscribeFromKeycloakEvent = subscribeToWidgetEvent(KEYCLOAK_EVENT_TYPE, (e) => {
            if (e.detail.eventType === "onReady") {
                this.keycloak = { ...getKeycloakInstance(), initialized: true }
                this.render()
            }
        })
    }
    render() {
        ReactDOM.render(<App config={this.#config} />, this.appendChild(this.mountPoint))
    }
}
customElements.get('et-strapi-template-app') || customElements.define("et-strapi-template-app", EtApp)