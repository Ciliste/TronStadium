package shesh.tron.screen.main.friend;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.adapter.SimpleListAdapter;
import com.kotcrab.vis.ui.util.dialog.ConfirmDialogListener;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.util.dialog.OptionDialogListener;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisList;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import shesh.tron.screen.context.ApiEndpointContext;
import shesh.tron.screen.context.TokenContext;
import shesh.tron.screen.panel.DebugPanel;
import shesh.tron.server.auth.token.Token;
import shesh.tron.server.auth.user.User;
import shesh.tron.utils.endpoint.ApiEndpoint;

public class FriendRequestsPopup extends DebugPanel {

    private ListView<User> friendRequestsList;
    private VisScrollPane friendRequestsScrollPane;
    private final ApiEndpointContext apiEndpointContext;
    private final TokenContext tokenContext;

    public FriendRequestsPopup(User[] friendRequests, ApiEndpointContext apiEndpointContext, TokenContext tokenContext) {

        super();

        this.apiEndpointContext = apiEndpointContext;
        this.tokenContext = tokenContext;

        setupListView(friendRequests);

        add(friendRequestsScrollPane).expand().fill();

        friendRequestsList.setItemClickListener(new ListView.ItemClickListener<User>() {

            @Override
            public void clicked(User item) {

                Dialogs.showConfirmDialog(getStage(), "Accept Friend Request", "Are you sure you want to accept " + item.getUsername() + "'s friend request?", new String[]{"Accept", "Decline"}, new Boolean[] {true, false}, new ConfirmDialogListener<Boolean>() {
                    @Override
                    public void result(Boolean result) {

                        Token tok = Token.of(tokenContext.getToken());
                        ApiEndpoint apiEndpoint = apiEndpointContext.getApiEndpoint();

                        if (result) {

                            apiEndpoint.acceptFriendRequest(tok, item.getUsername(), item.getId())
                                .then(success -> {

                                    if (success) {

                                        Dialogs.showOKDialog(getStage(), "Success", "You are now friends with " + item.getUsername() + "!");

                                        User[] newFriendRequests = new User[friendRequests.length - 1];
                                        int i = 0;
                                        for (User user : friendRequests) {

                                            if (!(user.getUsername().equals(item.getUsername()) && user.getId().equals(item.getId()))) {

                                                newFriendRequests[i] = user;
                                                i++;
                                            }
                                        }

                                        setupListView(newFriendRequests);
                                    }
                                    else {

                                        Dialogs.showErrorDialog(getStage(), "Error", "An error occurred while accepting " + item.getUsername() + "'s friend request.");
                                    }
                                })
                                .error(error -> {

                                    Dialogs.showErrorDialog(getStage(), "Error", "An error occurred while accepting " + item.getUsername() + "'s friend request.");
                                })
                                .executeAsync();
                        }
                        else {

                            apiEndpoint.declineFriendRequest(tok, item.getUsername(), item.getId())
                                .then(success -> {

                                    if (success) {

                                        Dialogs.showOKDialog(getStage(), "Success", "You declined " + item.getUsername() + "'s friend request.");

                                        User[] newFriendRequests = new User[friendRequests.length - 1];
                                        int i = 0;
                                        for (User user : friendRequests) {

                                            if (!(user.getUsername().equals(item.getUsername()) && user.getId().equals(item.getId()))) {

                                                newFriendRequests[i] = user;
                                                i++;
                                            }
                                        }

                                        setupListView(newFriendRequests);
                                    }
                                    else {

                                        Dialogs.showErrorDialog(getStage(), "Error", "An error occurred while declining " + item.getUsername() + "'s friend request.");
                                    }
                                })
                                .error(error -> {

                                    Dialogs.showErrorDialog(getStage(), "Error", "An error occurred while declining " + item.getUsername() + "'s friend request.");
                                })
                                .executeAsync();
                        }
                    }
                });

            }
        });
    }

    private void setupListView(User[] users) {

        SimpleListAdapter<User> adapter = new SimpleListAdapter<User>(new Array<User>(users));
        friendRequestsList = new ListView<User>(adapter);

        friendRequestsScrollPane = new VisScrollPane(friendRequestsList.getMainTable());
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initUIListeners() {

    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }

//    private class
}
