package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getByUserId(int UserId);

    void updateUser(int UserId,Profile profile);
}
