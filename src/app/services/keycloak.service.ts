import { Injectable } from '@angular/core';
import { KeycloakInstance } from 'keycloak-js';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  keycloak!: KeycloakInstance;

  constructor() { }

  init(): Promise<boolean> {
    return new Promise(async (resolve, reject) => {
      this.keycloak = new Keycloak({
        url: 'http://localhost:8180/auth', // ton serveur Keycloak
        realm: 'ged-realm',                // ton realm
        clientId: 'ged-frontend'            // ton client frontend
      });

      try {
        const authenticated = await this.keycloak.init({
          onLoad: 'login-required',
          checkLoginIframe: false
        });
        resolve(authenticated);
      } catch (error) {
        reject(error);
      }
    });
  }

  logout() {
    this.keycloak.logout();
  }

  getUsername(): string | undefined {
    return this.keycloak.tokenParsed?.['preferred_username'];
  }
}
