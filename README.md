# doconcensus
Manage reading a shared document and suggesting changes which apply upon voting conclusions via members.

## Workflow
- `VoteManager`, `DocManager`, `EndManager` are the three components that interact with each other for all actions. Their roles are:
  - `VoteManager`: Collect motion proposals and process them for conflicting changes (two users suggesting changes to the same section, same points), resolve them (Conflict Identifier - Conflict Resolver flow); forward passed proposals (as motion-in-vote) to users via Kafka; decide on voting conclusion based on ruleset; forward conclusion (if change accepted via motion vote success) to the `DocManager` and receive acknowledgement.
  - `DocManager`: Interface for all data load/store operations (along with metadata management), all changes to the doc will be stored as +/- instead of editing the root doc, so as to inherently maintain a version history. Each section will have a version and upon update, that version is broadcasted to all subscribers.
  - `EndManager` (# TODO: Change from `User` in DFD3): All read/vote operations interface facing the end user/delegate.

- User can read a section (meaning `EndManager` will subscribe to its topic in Kafka), suggest changes via motion proposals and vote on others' proposals while theirs are under voting.

- Still to finalize the exact setup of `DocManager`, hope to maintain proper Dependency Injection so when later DB choice is changed, do not have to re-write.

## Usecase
- Conferences, MUNs

### Why?
Why not?
_(Developing an understanding of how Kafka works with database stores and event-driven systems.)_

## Tech Stack
I hope to utilize here:
- MVC architecture
- Spring
- Kafka Connect
- Concurrency in Java
- CLI printing utilities
- Docker
- K8s (via minikube)

Will publish a proper FRD and ARD soon.
