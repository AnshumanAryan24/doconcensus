`VoteManager:`

| Request                     | Response      | Sample Req                                                    | Sample Res                                                                                                          |
|:----------------------------|:--------------|:--------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------|
| POST `/api/motions/propose` | Motion Object | Body: `{"motionId": 1, "section": 5, "changeText": "Update"}` | `{"motionId": 1, "stage": "PROPOSED", "section": 5, "changeText": "Update", "votesInFavour": 1, "votesAgainst": 0}` |
| GET `/api/motions/motion`   | Motion Object | URL: `/api/motions/motion?id=1`                               | `{"motionId": 1, "stage": "PROPOSED", "section": 5, "changeText": "Update", "votesInFavour": 1, "votesAgainst": 0}` |
