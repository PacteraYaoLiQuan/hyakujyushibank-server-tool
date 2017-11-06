package com.scsk.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final int changePasswordFlg;
    private final int change90DayFlg;

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}
     * .
     *
     * @param username
     *            tphe username presented to the
     *            <code>DaoAuthenticationProvider</code>
     * @param password
     *            the password that should be presented to the
     *            <code>DaoAuthenticationProvider</code>
     * @param lockStatus
     *            set to <code>true</code> if the user is enabled
     * @param accountNonExpired
     *            set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired
     *            set to <code>true</code> if the credentials have not expired
     * @param accountNonLocked
     *            set to <code>true</code> if the account is not locked
     * @param authorities
     *            the authorities that should be granted to the caller if they
     *            presented the correct username and assword and the user is
     *            enabled. Not null.
     * @param changePasswordFlg
     */
    public LoginUser(String username, String password, boolean lockStatus, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            int changePasswordFlg, int change90DayFlg) {
        super(username, password, lockStatus, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.changePasswordFlg = changePasswordFlg;
        this.change90DayFlg = change90DayFlg;
    }

    public int getChange90DayFlg() {
        return change90DayFlg;
    }
    
    public int getChangePasswordFlg() {
        return changePasswordFlg;
    }
}
