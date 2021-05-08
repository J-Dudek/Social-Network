export interface IInvit {
    t1: {
        idUser?: string;
        firstName?: string;
        lastName?: string;
        birthdate?: string;
        email?: string;
        phoneNumber?: string;
        city?: string;
        signInDate?: string;
        username?: string;
    },
    t2: {
        id?: number;
        firstUserId?: string;
        secondUserId?: string;
        date?: string;
        status?: boolean;
    }

}
