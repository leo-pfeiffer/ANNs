import json
from typing import Dict
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

OUT_FOLDER = 'evaluation/out'


def set_plt_params():
    major = 5.0
    minor = 3.0

    plt.style.use('default')
    plt.rcParams['font.size'] = 15
    plt.rcParams['legend.fontsize'] = 18
    plt.rcParams['text.usetex'] = True
    plt.rcParams['xtick.minor.size'] = minor
    plt.rcParams['xtick.major.size'] = major
    plt.rcParams['ytick.minor.size'] = minor
    plt.rcParams['ytick.major.size'] = major
    plt.rcParams['xtick.direction'] = 'in'
    plt.rcParams['ytick.direction'] = 'in'


# add margin to an axis subplot
# https://stackoverflow.com/a/34205235
def add_margin(ax, x=0.05, y=0.05):
    xlim = ax.get_xlim()
    ylim = ax.get_ylim()
    xmargin = (xlim[1]-xlim[0])*x
    ymargin = (ylim[1]-ylim[0])*y
    ax.set_xlim(xlim[0]-xmargin,xlim[1]+xmargin)
    ax.set_ylim(ylim[0]-ymargin,ylim[1]+ymargin)


def training_plot(data, title):
    ax = data.plot(x="index", y="Loss", legend=False, color='r')
    ax.set_ylabel("Loss")
    ax2 = ax.twinx()
    ax2.set_ylabel("Accuracy")
    ax2.set(ylim=(0, 1))
    add_margin(ax2, 0)
    data.plot(x="index", y=["Train Acc", "Dev Acc"], legend=False, ax=ax2)
    ax.figure.legend(loc='center right', bbox_to_anchor=(0.9, 0.35))
    plt.title(title)
    ax.set_xlabel("Epoch")
    plt.savefig(f"{OUT_FOLDER}/training-{title}.png", dpi=300, pad_inches=.15,
                bbox_inches='tight')
    plt.show()


def timing_plot(data):
    plt.subplots(figsize=(6, 5))
    g = sns.scatterplot(
        x='Sample size', y="value", hue="variable", data=data, s=50
    )
    g.set_xscale("log")
    g.set_yscale("log")
    plt.legend(title="Model")
    plt.ylabel(f'Time in ns', labelpad = 10)
    plt.savefig(f"{OUT_FOLDER}/timing.png", dpi=300, pad_inches=.15,
                bbox_inches='tight')
    plt.show()


def tuning_plot(data, title):
    xsize = 15
    ysize = 5

    fig, axs = plt.subplots(figsize=(xsize, ysize), ncols=3)
    sns.regplot(x='learningRate', y='accuracy', data=data, ax=axs[0])
    sns.regplot(x='sizeFirstHiddenLayer', y='accuracy', data=data, ax=axs[1])
    sns.regplot(x='sizeOtherHiddenLayers', y='accuracy', data=data, ax=axs[2])

    for ax in axs:
        ax.set(ylim=(0, 1))

    plt.suptitle(title)

    plt.savefig(f"{OUT_FOLDER}/tuning-{title}.png", dpi=300, pad_inches=.15,
                bbox_inches='tight')

    plt.show()


def parse_json(path: str) -> Dict:
    with open(path, 'r') as f:
        data = json.load(f)
        return data


def tuning_df(jsn: Dict):
    df = pd.DataFrame(jsn["settings"])
    df["accuracy"] = list(jsn["vals"].values())
    return df


if __name__ == '__main__':
    set_plt_params()
    jsn = parse_json("out/_tuning1.json")
    t1 = tuning_df(jsn)
    tuning_plot(t1, "Part 1")

    jsn = parse_json("out/_tuning3.json")
    t3 = tuning_df(jsn)
    tuning_plot(t3, "Part 3")

    jsn = parse_json("out/_tuning4.json")
    t4 = tuning_df(jsn)
    tuning_plot(t4, "Part 4")

    timing = pd.read_csv("out/timer.csv")
    timing = pd.melt(timing, id_vars=["Sample size"], value_vars=["Part 1", "Part 2"])
    timing_plot(timing)

    p1 = pd.read_csv('out/_part1.csv').reset_index()
    training_plot(p1, "Part 1")

    p3 = pd.read_csv('out/_part3.csv').reset_index()
    training_plot(p3, "Part 3")

    p4 = pd.read_csv('out/_part4.csv').reset_index()
    training_plot(p4, "Part 4")
