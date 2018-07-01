package com.solo.lifetoday.views.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.solo.lifetoday.R;
import com.solo.lifetoday.presenters.SignInPresenter;
import com.solo.lifetoday.views.Entries.EntryListActivity;

/**
 * @author eddy.
 */

public class SignInFragment extends Fragment implements SignInPresenter.View {
    private static final int GOOGLE_SIGN_IN_RETURN_CODE = 34;
    private static String TAG = SignInFragment.class.getSimpleName();
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;

    private View mFragmentView;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure sign-in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            openEntries();
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        mFragmentView = inflater.inflate(R.layout.signin_fragment, container, false);

        initUI();

        return mFragmentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_RETURN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                fireBaseAuthWithGoogle(googleSignInAccount);
            } catch (ApiException e) {
                // Google Sign In failed
                Log.w(TAG, "Google sign in failed");
            }
        }
    }

    /**
     * Initialize UI and bind to views
     */
    private void initUI() {
        SignInButton signInButton = mFragmentView.findViewById(R.id.googleSignInButton);
        signInButton.setOnClickListener((view) -> signIn());

        mProgressBar = mFragmentView.findViewById(R.id.signInProgressBar);
    }

    /**
     * Create and issue intent to sign in using the user's google account
     */
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_RETURN_CODE);
    }

    /**
     * Attempt to authenticate user using firebase google sign in
     *
     * @param googleSignInAccount The user's google account
     */
    private void fireBaseAuthWithGoogle(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "FirebaseAuthWithGoogle" + googleSignInAccount.getEmail());

        showProgressBar();

        AuthCredential authCredential = GoogleAuthProvider.getCredential(
                googleSignInAccount.getIdToken(),
                null);
        mFirebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(
                        requireActivity(),
                        this::onSignInCompletion);
    }

    /**
     * Specifies what to do after completing the sign in task depending
     * on whether or not it succeeded or failed
     *
     * @param task SignIn Task
     */
    private void onSignInCompletion(@NonNull Task<AuthResult> task) {
        hideProgressBar();

        if (task.isSuccessful()) {
            // Sign in success
            Log.d(TAG, "signInWithCredential: success");
            openEntries();
        } else {
            Log.w(TAG,
                    "Signin with credentials failed",
                    task.getException());
            Snackbar.make(
                    mFragmentView,
                    "Authentication Failed.",
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

    /**
     * Starts the entries list activity
     */
    private void openEntries() {
        Intent entryListIntent = new Intent(requireActivity(), EntryListActivity.class);
        startActivity(entryListIntent);
        requireActivity().finish();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
