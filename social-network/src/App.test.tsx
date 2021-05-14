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
import PersonnalPost from './components/fonctionnals/personnalPost'


/** DONNEES POUR TESTS */
import { IUser } from './types/IUser'
import { IInvit } from './types/IInvit'
import { IPost } from './types/IPost'
import HomePage from './components/home/homePage';
import UserPagePublic from './components/friend/UserPagePublic';
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
const postTest: IPost = {
  idPost: 42,
  message: "Your time is limited, don't waste it by leading an existence that is not yours.",
  public: true,
  publicationDate: "2021-05-10",
  userId: "SteveJobs",
}
let promiseFunc: () => Promise<void>;


describe('Header', () => {
  it('should display Login button', () => {
    render(
      <LogIn />,
    );

    expect(screen.getByText(/Log In/i)).toBeInTheDocument();
  });

  it('should display Profile button', () => {
    render(
      <ProfileButton /> ,
    );

    expect(screen.getByText(/Profile/i)).toBeInTheDocument();
  });

  it('should display Sign Up button', () => {
    render(
      <Header /> ,
    );

    expect(screen.getByText(/Sign Up/i)).toBeInTheDocument();
  });

});

describe('Profile', () => {
  it('Can see info on profile', () => {
    render(
      <BrowserRouter><Friend user={userTest} /></BrowserRouter>,
    );

    expect(screen.getByText(/John Doe/i)).toBeInTheDocument();
  });

});

describe('Invitation received', () => {
  it('Can see boutton decline on invitation received', () => {
    render(
      <BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Decline/i)).toBeInTheDocument();
  });

  it('Can see boutton accept on invitation received', () => {
    render(
      <BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Accept/i)).toBeInTheDocument();
  });

  it('Can see user infos on invitation received', () => {
    render(
      <BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Morgan Freeman/i)).toBeInTheDocument();
  });

  it('Can see username infos on invitation received', () => {
    render(
      <BrowserRouter><InvitationR invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Free Morgan/i)).toBeInTheDocument();
  });

});

describe('Invitation sent', () => {
  it('Can see info on profile', () => {
    render(
      <BrowserRouter><InvitationS invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Morgan Freeman/i)).toBeInTheDocument();
  });

  it('Can see button cancel on invitation sent', () => {
    render(
      <BrowserRouter><InvitationS invit={invitTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Cancel/i)).toBeInTheDocument();
  });
});

describe('Card Profil', () => {
  it('Can see firstname and lastname on card Profil', () => {
    render(
      <BrowserRouter><CardProfil user={userTest} friend={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/Doe John/i)).toBeInTheDocument();
  });

  it('Can see city and signin date on card Profil', () => {
    render(
      <BrowserRouter><CardProfil user={userTest} friend={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/Hi, I'm John, I'm leave in Road./i)).toBeInTheDocument();
  });

  it('Can see phone number and lastname on card Profil', () => {
    render(
      <BrowserRouter><CardProfil user={userTest} friend={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/0000000000/i)).toBeInTheDocument();
  });

  it('Can see email on card Profil', () => {
    render(
      <BrowserRouter><CardProfil user={userTest} friend={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/toto@tata.titi/i)).toBeInTheDocument();
  });
});

describe('Personnal post', () => {
  it('Can see message on personnal Post', () => {
    render(
      <BrowserRouter><PersonnalPost post={postTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Your time is limited, don't waste it by leading an existence that is not yours./i)).toBeInTheDocument();
  });

  it('Can see publication date on personnal Post', () => {
    render(
      <BrowserRouter><PersonnalPost post={postTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/2021-05-10/i)).toBeInTheDocument();
  });

  it('Can see public label on personnal public Post', () => {
    render(
      <BrowserRouter><PersonnalPost post={postTest} updateParent={promiseFunc} /></BrowserRouter>,
    );

    expect(screen.getByText(/Public/i)).toBeInTheDocument();
  });

});

describe('HomePage', () => {
  it('Can see Search friends', () => {
    render(
      <BrowserRouter><HomePage register={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/Search friends/)).toBeInTheDocument();
  });

  it('Can see Friends section', () => {
    render(
      <BrowserRouter><HomePage register={true} /></BrowserRouter>,
    );

    expect(screen.getByText('Friends')).toBeInTheDocument();
  });

  it('Can see Pending invitation section', () => {
    render(
      <BrowserRouter><HomePage register={true} /></BrowserRouter>,
    );

    expect(screen.getByText('Pending invitation')).toBeInTheDocument();
  });

  it('Can see Friends posts section', () => {
    render(
      <BrowserRouter><HomePage register={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/Friends' posts/)).toBeInTheDocument();
  });

  it('Can see Received Invitations section', () => {
    render(
      <BrowserRouter><HomePage register={true} /></BrowserRouter>,
    );

    expect(screen.getByText(/Received Invitations/)).toBeInTheDocument();
  });
});
