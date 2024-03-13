# Auction System

* This project consists of a simple client-server architecture to facilitate bidding in an auction. The system allows multiple clients to connect to the server, submit bids, and receive notifications about other bids.

## How it Works
* The AuctionServer listens for incoming client connections on a specified port.
Each client connection is handled by a separate ClientHandler thread.
The ClientHandler receives bids from clients, notifies other clients about new bids, and terminates upon receiving the "end" command.
The AuctionClient connects to the server, allows users to input bids, and displays notifications from the server.