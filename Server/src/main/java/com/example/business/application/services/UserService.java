package com.example.business.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.business.application.common.FileMethod;
import com.example.business.application.models.userModels.EditUserModel;
import com.example.business.application.models.userModels.UserProfileModel;
import com.example.business.data.entities.UserInformationPast;
import com.example.business.data.repositories.implement.IUserInformationPastRepository;
import com.example.business.data.repositories.implement.IUserRepository;

@Service
public class UserService {
    // #region Repositories
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserInformationPastRepository userInformationPastRepository;
    // #endregion

    // #region Checking

    // #endregion

    // #region Business
    public UserProfileModel GetUser(String tokenAccess) {
        var user = userRepository.findByTokenAccess(tokenAccess);
        if (user == null)
            return null;
        var userProfile = new UserProfileModel();
        userProfile.displayName = user.getDisplayName();
        userProfile.fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        userProfile.firstName = user.getFirstName();
        userProfile.lastName = user.getLastName();
        userProfile.email = user.getEmail();
        userProfile.phoneNumber = user.getPhoneNumber();
        userProfile.dateOfBirth = String.valueOf(user.getDOB());
        userProfile.isVerifyEmail = user.getIsVerified();
        userProfile.facebook = user.getFacebook();
        userProfile.tikTok = user.getTikTok();
        userProfile.twitter = user.getTwitter();
        userProfile.instagram = user.getInstagram();
        return userProfile;
    }

    public UserProfileModel EditUser(EditUserModel editUserModel) {
        var user = userRepository.findByTokenAccess(editUserModel.tokenAccess);
        if (user == null)
            return null;
        // store old information
        var userPast = new UserInformationPast();
        userPast.setLastDisplayName(user.getDisplayName());
        userPast.setLastFirstName(user.getFirstName());
        userPast.setLastLastName(user.getLastName());
        userPast.setUser(user);
        userInformationPastRepository.save(userPast);
        // update information
        user.setDisplayName(editUserModel.newDisplayName);
        user.setFirstName(editUserModel.newFirstName);
        user.setLastName(editUserModel.newLastName);
        user.setDOB(LocalDateTime.parse(editUserModel.newDateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        userRepository.save(user);
        // return
        var userProfile = GetUser(editUserModel.tokenAccess);
        return userProfile;
    }

    public boolean UploadAvatar(MultipartFile file, String tokenAccess) {
        var user = userRepository.findByTokenAccess(tokenAccess);
        if (user == null)
            return false;
        var avatarName = user.getAvatar();
        var result = FileMethod.saveMultipartFile(file, avatarName);
        if (result.isEmpty() || result == null)
            return false;
        return true;
    }

    public UserProfileModel EditEmail(String email, String tokenAccess) {
        var user = userRepository.findByTokenAccess(tokenAccess);
        if (user == null)
            return null;
        // store old email
        var userPast = new UserInformationPast();
        userPast.setLastEmail(user.getEmail());
        userPast.setUser(user);
        userInformationPastRepository.save(userPast);
        // update email
        user.setEmail(email);
        userRepository.save(user);
        // return
        var userProfile = new UserProfileModel();
        userProfile.displayName = user.getDisplayName();
        userProfile.fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        userProfile.email = user.getEmail();
        userProfile.phoneNumber = user.getPhoneNumber();
        userProfile.dateOfBirth = String.valueOf(user.getDOB());
        return userProfile;
    }

    // #endregion
}
