import React from 'react';
import { render, screen } from '@testing-library/react';
import LogIn from "./components/header/login-button";
import ProfileButton from "./components/header/profile-button"
import Header from "./header"
import Friend from "./components/fonctionnals/friend"
import { BrowserRouter } from 'react-router-dom';
import InvitationR from './components/fonctionnals/invitationR'
import InvitationS from './components/fonctionnals/invitationS'
import CardProfil from './components/fonctionnals/cardProfil'


/** DONNEES POUR TESTS */
import { IUser } from './types/IUser'
import { IInvit } from './types/IInvit'
const userTest: IUser = {
  lastName: "Doe",
  firstName: "John",
  idUser: "the one",
  city: "Road",
  phoneNumber: "0000000000",
  email: "toto@tata.titi",
  signInDate: "2021-05-10"
}
const invitTest: IInvit = {
  t1: {
    firstName: "Morgan",
    lastName: "Freeman",
    username: "Free Morgan",


  },
  t2: {
    firstUserId: "the one",
  }
}
let promiseFunc: () => Promise<void>;


/**        HEADER          */
test('Log in Button', () => {
  render(<LogIn />);
  const linkElement = screen.getByText("Log In")
  expect(linkElement).toBeInTheDocument();
});

test('Profile button', () => {
  render(<ProfileButton />);
  const linkElement = screen.getByText("Profile")
  expect(linkElement).toBeInTheDocument();
});

test('Header Sign up text (include button Sign up)', () => {
  render(<Header />);
  const linkElement = screen.getByText("Sign Up")
  expect(linkElement).toBeInTheDocument();
});
/**        PROFILE          */
test('Can see info on profile)', () => {
  render(<BrowserRouter><Friend user={userTest} /></BrowserRouter>);
  const linkElement = screen.getByText("John Doe")
  expect(linkElement).toBeInTheDocument();
});
/**        INVITATION   RECEIVED       */
test('Can see boutton decline on invitation received', () => {
  render(<BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Decline")
  expect(linkElement).toBeInTheDocument();
});
test('Can see boutton accept on invitation received', () => {
  render(<BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Accept")
  expect(linkElement).toBeInTheDocument();
});
test('Can see user infos on invitation received', () => {
  render(<BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Morgan Freeman")
  expect(linkElement).toBeInTheDocument();
});
test('Can see username infos on invitation received', () => {
  render(<BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Free Morgan")
  expect(linkElement).toBeInTheDocument();
});
/**        INVITATION   SENT       */
test('Can see user on invitation sent', () => {
  render(<BrowserRouter><InvitationS invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Morgan Freeman")
  expect(linkElement).toBeInTheDocument();
});
test('Can see button cancel on invitation sent', () => {
  render(<BrowserRouter><InvitationS invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>);
  const linkElement = screen.getByText("Cancel")
  expect(linkElement).toBeInTheDocument();
});
/**        CARD PROFIL      */
test('Can see firstname and lastname on card Profil', () => {
  render(<BrowserRouter><CardProfil user={userTest} /></BrowserRouter>);
  const linkElement = screen.getByText("John Doe")
  expect(linkElement).toBeInTheDocument();
});
test('Can see city and signin date on card Profil', () => {
  render(<BrowserRouter><CardProfil user={userTest} /></BrowserRouter>);
  const linkElement = screen.getByText("Lives in : Road Join : 2021-05-10")
  expect(linkElement).toBeInTheDocument();
});
test('Can see phone number and lastname on card Profil', () => {
  render(<BrowserRouter><CardProfil user={userTest} /></BrowserRouter>);
  const linkElement = screen.getByText("Phone : 0000000000")
  expect(linkElement).toBeInTheDocument();
});

test('Can see email on card Profil', () => {
  render(<BrowserRouter><CardProfil user={userTest} /></BrowserRouter>);
  const linkElement = screen.getByText("Email : toto@tata.titi")
  expect(linkElement).toBeInTheDocument();
});
