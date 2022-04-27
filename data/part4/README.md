# README for PART 4

Note: to run the python script, install the dependencies specified in `requirements.txt`

This directory contains the script (`word2vec.py`) extracts a subset from the 
Google News word2vec file. The resulting vocabulary contains all words
of the vocabulary of part 1 that are also included in the word2vec file.

The script also takes in the raw TREC datasets (from the `original` directory)
and re-encodes them using the new vocabulary.

To run the scrip, the Google News word2vec file must be in this directory 
and be named `GoogleNews-vectors-negative300.bin.gz`.

This will create the following files in the `output` directory:

- Data sets encoded with indices of the new vocabulary
    - dev.txt
    - test.txt
    - train.txt
- Word2vec based model outputs
    - subset_model.kv (Python-readable KeyedVector model)
    - subset_model.txt (Embedding matrix with same format as GloVe)