import csv
import logging
from typing import Dict, List
from gensim.models import KeyedVectors

# set logging level to info to observe progress
logging.basicConfig(level=logging.INFO)

# model files
word2vec_file = "GoogleNews-vectors-negative300.bin.gz"
subset_model_file = "output/subset_model.kv"
subset_model_txt_file = "output/subset_model.txt"


def load_vocab(path: str) -> List:
    """
    Load a vocabulary file.
    """
    logging.info(f"reading file {path}")
    words = []
    with open(path, encoding="utf-8") as f:
        for line in f:
            word = line.split(" ")[0].strip()
            words.append(word)
    return words


def load_word2vec(path: str) -> KeyedVectors:
    """
    Load a word2vec file. This might take a few seconds to a minute.
    """
    logging.info(f"reading file {path}")
    model = KeyedVectors.load_word2vec_format(path, binary=True)
    return model


def subset(model: KeyedVectors, words: List) -> KeyedVectors:
    """
    Create a KeyedVector for all words that occur in the vocab and the model
    :param model: pre-trained model
    :param words: vocab
    :return: subset model
    """
    logging.info("creating subset model")
    subset_model = KeyedVectors(model.vector_size)

    keys = []
    vectors = []

    for word in words:
        if word in model.key_to_index:
            keys.append(word)
            index = model.key_to_index[word]
            vectors.append(model.vectors[index])

    subset_model.add_vectors(keys, vectors)
    return subset_model


def kv_to_txt(model: KeyedVectors, path: str) -> None:
    """
    Write a KeyedVector file to txt in the same format as the GloVe data set
    from part 3.
    """
    logging.info(f"writing file {path}")
    with open(path, 'w', newline='') as f:
        writer = csv.writer(f, delimiter=' ')
        for i in range(len(model.index_to_key)):
            row = [model.index_to_key[i]] + list(model.vectors[i])
            writer.writerow(row)


def read_classes(path: str) -> Dict:
    """
    Read a classes.txt file
    """
    logging.info(f"reading file {path}")
    class_to_index = {}
    with open(path, encoding="us-ascii") as f:
        for i, line in enumerate(f):
            class_name = line.strip().lower()
            class_to_index[class_name] = i
    return class_to_index


def create_value_to_index(index_to_value: List) -> Dict:
    """
    For a list of unique items, create a dict that maps from value to the index.
    :param index_to_value: list of unique elements
    :return: dict
    """
    value_to_index = {}
    for i, word in enumerate(index_to_value):
        value_to_index[word] = i
    return value_to_index


def read_original(path: str) -> List:
    """
    Read a TREC file.
    """
    logging.info(f"reading file {path}")
    original = []
    with open(path, encoding="us-ascii") as f:
        for line in f:
            original.append([x.strip().lower() for x in line.split(" ")])
    return original


def convert_original(original: List, class_to_index: Dict,
                     word_to_index: Dict) -> List:
    """
    Convert a TREC file to a list of indices pointing to the words in the vocab
    in the same way as the files given for part 1.
    """
    converted = []
    for line in original:
        category = class_to_index[line[0]]
        encoded_words = []
        for word in line[1:]:
            if word in word_to_index:
                encoded_words.append(word_to_index[word])
        converted.append({'category': category, 'encoded_words': encoded_words})
    return converted


def converted_to_txt(converted: List, path: str) -> None:
    """
    Convert a converted TREC file to txt.
    """
    logging.info(f"writing file {path}")
    with open(path, 'w', newline='') as f:
        for row in converted:
            encoded_words = [str(w) for w in row['encoded_words']]
            row_str = " ".join(encoded_words) + " ; " + str(row["category"])
            f.write(row_str)
            f.write("\n")


if __name__ == '__main__':
    # Load the vocab list required
    vocab = load_vocab("input/vocab.txt")

    # Load google word2vec file
    m = load_word2vec(word2vec_file)

    # Create subset from word2vec file
    sm = subset(m, vocab)

    # # Save to subset file
    sm.save(subset_model_file)
    # Load the subset file
    sm = KeyedVectors.load(subset_model_file)

    # Convert subset model to txt file
    kv_to_txt(sm, subset_model_txt_file)

    # create new vocabulary = {w | w in intersection(w2v, vocab)}
    new_vocab = sm.index_to_key
    words_to_index = create_value_to_index(new_vocab)

    # load original data
    classes_to_index = read_classes("input/classes.txt")
    dev_data = read_original("input/dev.txt")
    test_data = read_original("input/test.txt")
    train_data = read_original("input/train.txt")

    # convert into indexed files
    dev_conv = convert_original(dev_data, classes_to_index, words_to_index)
    test_conv = convert_original(test_data, classes_to_index, words_to_index)
    train_conv = convert_original(train_data, classes_to_index, words_to_index)

    # write indexed files to txt
    converted_to_txt(dev_conv, 'output/dev.txt')
    converted_to_txt(test_conv, 'output/test.txt')
    converted_to_txt(train_conv, 'output/train.txt')
