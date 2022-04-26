import csv
from gensim.models import KeyedVectors

word2vec_file = "GoogleNews-vectors-negative300.bin.gz"
vocab_file = "glove_vocab.txt"
vocab_kv = "vocab.kv"
word2vec_out = "vocab_word2vec.txt"


def load_vocab(path):
    words = set()
    with open(path, encoding="utf-8") as f:
        for line in f:
            word = line.split(" ")[0]
            words.add(word)
    return words


def load_word2vec(path):
    print("loading file...")
    model = KeyedVectors.load_word2vec_format(path, binary=True)
    print("done...")
    return model


def subset(model: KeyedVectors, restricted_word_set):
    subset_model = KeyedVectors(model.vector_size)

    keys = []
    vectors = []

    for i in range(len(model.index_to_key)):
        word = model.index_to_key[i]
        vec = model.vectors[i]
        if word in restricted_word_set:
            keys.append(word)
            vectors.append(vec)

    subset_model.add_vectors(keys, vectors)

    return subset_model


def kv_to_txt(model: KeyedVectors, path: str):
    with open(path, 'w', newline='') as f:
        writer = csv.writer(f, delimiter=' ')
        for i in range(len(model.index_to_key)):
            row = [model.index_to_key[i]] + list(model.vectors[i])
            writer.writerow(row)


if __name__ == '__main__':
    # Load the vocab list required
    words = load_vocab(vocab_file)

    # Load google word2vec file
    m = load_word2vec(word2vec_file)

    # Create subset from word2vec file of words that occur in vocab
    sm = subset(m, words)

    # # Save to subset file
    # sm.save(vocab_kv)
    # # Load the subset file
    # sm = KeyedVectors.load(vocab_kv)

    # Convert to txt file
    kv_to_txt(sm, word2vec_out)
