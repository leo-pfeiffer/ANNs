# README for PART 4

This directory contains the script (`word2vec.py`) extracts a subset from the 
Google News word2vec file. The resulting vocabulary contains all words
of the word2vec vocabulary that are also included in the GloVe vocabulary of 
Part 3. The file GloVe vocabulary is copied into the `part4` directory as 
`glove_vocab`. 

To run the scrip, the Google News word2vec file must be in this directory 
and be named `GoogleNews-vectors-negative300.bin.gz`.

Crucially, this will create the file `vocab_word2vec.txt`, which can be used
as input for the model of Part 4. The file structure corresponds
to that of the GloVe file.