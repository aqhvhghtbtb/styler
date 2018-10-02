# -*- coding: utf-8 -*-

import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

import numpy as np

import random
import colorsys
import sys
import os
import json
import datetime

from functools import reduce

def protocol6(results, repair_tool):
    error_type_repair = dict()
    for result in results:
        file_with_cs_errors = result["file_with_cs_errors_{}".format(repair_tool)]
        for file, liste in result["file_with_cs_errors_ugly"].items():
            for modification in liste:
                errors = list(set([error["source"].split(".")[-1] for error in modification["errors"]]))
                errors_after_repair = []
                if file in file_with_cs_errors:
                    modification_repaired = list(filter(lambda x: x["type"] == modification["type"] and x["modification_id"] == modification["modification_id"], file_with_cs_errors[file]))
                    if len(modification_repaired):
                        if len( modification_repaired[0]["errors"]):
                            errors_after_repair = list(set([error["source"].split(".")[-1] for error in modification_repaired[0]["errors"]]))
                        else:
                            print("dgdfg")
                            errors_after_repair = errors


                for error in errors:
                    if error not in error_type_repair:
                        error_type_repair[error] = {"repaired": 0, "other_errors": 0, "new_errors": 0, "not_repaired": 0}
                    if error in errors_after_repair:
                        error_type_repair[error]["not_repaired"] += 1
                    else:
                        if len(errors_after_repair):
                            if len(list(filter(lambda x: x not in errors, errors_after_repair))):
                                error_type_repair[error]["new_errors"] += 1
                            else:
                                error_type_repair[error]["other_errors"] += 1
                        else:
                            error_type_repair[error]["repaired"] += 1
    def f(x):
        s = sum(x.values())
        obj = {key: (value / s) for key, value in x.items()}
        obj["sum"] = s
        return obj
    error_type_repair = {key:f(value) for key, value in error_type_repair.items()}

    objects = error_type_repair.keys()
    y_pos = np.arange(len(objects))
    types = ("repaired", "other_errors", "new_errors", "not_repaired")
    colors = {"repaired": "#2ecc71", "new_errors": "#f39c12", "other_errors": "#3498db", "not_repaired": "#e74c3c"}
    sum_left = [0] * len(error_type_repair)
    for type in types:
        performance = [item[type] for item in error_type_repair.values()]
        plt.barh(y_pos, performance, align='center', color=colors[type], left=sum_left, label=type)
        sum_left = list(map(lambda x, y: x+y, sum_left, performance))

    # plt.barh(y_pos, [item["other_errors"] for item in error_type_repair.values()], align='center', left=performance, color="#f39c12", label="Partially repaired")
    plt.yticks(y_pos, objects, rotation=0)
    plt.xlabel('Usage')
    plt.title('Percentage of repaired checkstyle errors.')
    plt.legend()

def plot_repaired_files(results):
    modifications = (2,2,2,2,2)

    counts = ('naturalize', 'naturalize_snipper', 'codebuff', 'codebuff_snipper')

    barWidth = 1. / (len(counts) + 1)
    bars = [[] for i in range(len(counts)) ]

    labels = []

    for result in results:
        # labels.append( exp.corpus.name + "(" + str(exp.corpus.get_number_of_files()) + " files)" )
        with_errors = result["number_of_injections"] * result["corrupted_files_ratio_ugly"]
        labels.append("{}, \n /{} injections".format(result["name"], int(with_errors)))
        for count, i in zip(counts, range(len(counts))):
            prop = result["corrupted_files_ratio_" + count]
            bars[i].append( 1 - ( result["number_of_injections"] * result["corrupted_files_ratio_" + count]) / with_errors )
            # bars[i].append( result["corrupted_files_ratio_" + count] )

    # Set position of bar on X axis
    r = []
    r.append(np.arange(len(labels)))
    for i in range(1,len(counts)):
        r.append([x + barWidth for x in r[i-1]])


    # Make the plot
    for count, i in zip(counts, range(len(counts))):
        plt.bar(r[i], bars[i], width=barWidth, edgecolor='white', label=count)


    # Add xticks on the middle of the group bars
    plt.xlabel('Proportion of files with errors (m=' + str(modifications) + ')', fontweight='bold')
    plt.xticks([r + barWidth * (len(counts)-1) / 2 for r in range(len(results))], labels, rotation=45, fontsize=8)
    plt.subplots_adjust(bottom=0.30)
    # Create legend & Show graphic
    plt.legend()

def plot_diffs(results):
    modifications = (2,2,2,2,2)

    counts = ('naturalize', 'naturalize_snipper', 'codebuff', 'codebuff_snipper')

    barWidth = 1. / (len(counts) + 1)
    bars = [[] for i in range(len(counts)) ]

    labels = []

    for result in results:
        # labels.append( exp.corpus.name + "(" + str(exp.corpus.get_number_of_files()) + " files)" )
        with_errors = result["number_of_injections"] * result["corrupted_files_ratio_ugly"]
        labels.append("{}, \n /{} injections".format(result["name"], int(with_errors)))
        for count, i in zip(counts, range(len(counts))):
            bars[i].append( result["diffs_avg_" + count] )
            # bars[i].append( result["corrupted_files_ratio_" + count] )

    # Set position of bar on X axis
    r = []
    r.append(np.arange(len(labels)))
    for i in range(1,len(counts)):
        r.append([x + barWidth for x in r[i-1]])


    # Make the plot
    for count, i in zip(counts, range(len(counts))):
        plt.bar(r[i], bars[i], width=barWidth, edgecolor='white', label=count)


    # Add xticks on the middle of the group bars
    plt.xlabel('Proportion of files with errors (m=' + str(modifications) + ')', fontweight='bold')
    plt.xticks([r + barWidth * (len(counts)-1) / 2 for r in range(len(results))], labels, rotation=45, fontsize=8)
    plt.subplots_adjust(bottom=0.30)
    # Create legend & Show graphic
    plt.legend()

def plot_percentage_of_errors(results):
    modifications = (5,5)

    barWidth = 0.25
    bars1 = []
    naturalize_res = []
    codebuff_res = []
    labels = []

    for result in results:
        # labels.append( exp.corpus.name + "(" + str(exp.corpus.get_number_of_files()) + " files)" )
        labels.append(result["name"])
        bars1.append( result["corrupted_files_ratio_ugly"] )
        naturalize_res.append( result["corrupted_files_ratio_naturalize"] )
        codebuff_res.append( result["corrupted_files_ratio_codebuff"] )


    # Set position of bar on X axis
    r1 = np.arange(len(bars1))
    r2 = [x + barWidth for x in r1]
    r3 = [x + barWidth for x in r2]


    # Make the plot
    plt.bar(r1, bars1, color='#3498db', width=barWidth, edgecolor='white', label='Error injection')
    plt.bar(r2, naturalize_res, color='#f1c40f', width=barWidth, edgecolor='white', label='Naturalize')
    plt.bar(r3, codebuff_res, color='#1abc9c', width=barWidth, edgecolor='white', label='Codebuff')


    # Add xticks on the middle of the group bars
    plt.xlabel('Number of fully patched files (m=' + str(modifications) + ')', fontweight='bold')
    plt.xticks([r + barWidth for r in range(len(bars1))], labels, rotation=45, fontsize=8)
    plt.subplots_adjust(bottom=0.30)
    # Create legend & Show graphic
    plt.legend()

def plot_errors_types(results, counts): # protocol1
    modifications = (2,2,2,2,2)

    labels = []

    errors_labels = set()

    for result in results:
        # labels.append( exp.corpus.name + "(" + str(exp.corpus.get_number_of_files()) + " files)" )
        labels.append("{} ({})".format(result["name"], result["number_of_injections"]))
        for count in counts:
            errors_labels = errors_labels | result[count].keys()

    total_error_count = dict()
    for error in errors_labels:
        total_error_count[error] = 0
    for result in results:
        for count in counts:
            for error, n in result[count].items():
                total_error_count[error] += n
    sum_total_error_count = sum(total_error_count.values())

    n_errors_labels = len(errors_labels)
    colors = []
    if ( n_errors_labels > 1):
        for i in range( 0, n_errors_labels ):
            colors.append('#%02x%02x%02x' % tuple(map(lambda x: int( x*256 ), colorsys.hls_to_rgb( 1 / (n_errors_labels-1) * i * 0.9 , random.uniform(0.4, 0.6), random.uniform(0.4, 0.6)))))
        random.shuffle(colors)
    else :
        colors.append('#ff00ff')

    lables_colors = dict()
    i = 0
    for error_label in errors_labels:
        lables_colors[error_label] = colors[i]
        i += 1

    def compute_errors_layer(errors_count_name):
        layers = dict()
        for result in results:
            errors = result[errors_count_name]
            for error_label in errors_labels:
                if ( error_label not in layers):
                    layers[error_label] = []
                if ( error_label in errors ):
                    layers[error_label].append(errors[error_label])
                else:
                    layers[error_label].append(0)
        return layers

    layers = dict()
    for count in counts:
        layers[count] = compute_errors_layer(count)


    barWidth = 1. / (len(counts) + 1)
    # Set position of bar on X axis
    r = []
    r.append(np.arange(len(labels)))
    for i in range(1,len(counts)):
        r.append([x + barWidth for x in r[i-1]])


    def add_layers_to_the_graph(layers, position):
        sum = [0] * len(labels)
        for key, values in layers.items():
            plt.bar(position, values, width=barWidth, color=lables_colors[key], bottom=sum, edgecolor='white')
            sum = list(map( lambda x, y: x + y, sum, values))
        return sum
    # plt.bar(r2, naturalize_res, color='#f1c40f', width=barWidth, edgecolor='white', label='Naturalize')
    # plt.bar(r3, codebuff_res, color='#1abc9c', width=barWidth, edgecolor='white', label='Codebuff')
    i = 0
    sums = []
    for count, i in zip(counts, range(len(counts))):
        sums.append(add_layers_to_the_graph(layers[count], r[i]))


    # Add xticks on the middle of the group bars
    plt.xlabel('Number of errors (m={}) \n {}'.format(modifications, counts), fontweight='bold')
    plt.xticks([r + barWidth * (len(counts)-1) / 2 for r in range(len(results))], labels, rotation=45, fontsize=8)
    plt.subplots_adjust(top=0.80)

    plt.subplots_adjust(bottom=0.30)
    # Create legend & Show graphic
    patches = [ mpatches.Patch(color=c, label="{} ({:.2f}%)".format(l.split(".")[-1], total_error_count[l] / sum_total_error_count * 100)) for l, c in lables_colors.items()]
    plt.legend(handles = patches, loc='upper center', ncol=3, fancybox=True, bbox_to_anchor=(0.5, 1.4))

def plot_errors_types_per_injection_type(results):
    modifications = (2,2,2,2,2)

    counts = ("insertions-newline", "insertions-space", "insertions-tab", "deletions-newline", "deletions-space")

    labels = []
    errors_labels = set()

    for result in results:
        # labels.append( exp.corpus.name + "(" + str(exp.corpus.get_number_of_files()) + " files)" )
        labels.append("{} ({})".format(result["name"], result["number_of_injections"]))
        errors_labels = errors_labels | result["checkstyle_errors_count_ugly"].keys()

    n_errors_labels = len(errors_labels)
    colors = []
    if ( n_errors_labels > 1):
        for i in range( 0, n_errors_labels ):
            colors.append('#%02x%02x%02x' % tuple(map(lambda x: int( x*256 ), colorsys.hls_to_rgb( 1 / (n_errors_labels-1) * i * 0.9 , random.uniform(0.4, 0.6), random.uniform(0.4, 0.6)))))
        random.shuffle(colors)
    else :
        colors.append('#ff00ff')

    lables_colors = dict()
    i = 0
    for error_label in errors_labels:
        lables_colors[error_label] = colors[i]
        i += 1

    def compute_error_origines(result):
        result["errors_origine"] = dict()
        for file_with_cs_errors in result["file_with_cs_errors_ugly"].values():
            for file_modification in file_with_cs_errors:
                type = file_modification["type"]
                if (type not in result["errors_origine"]):
                    result["errors_origine"][type] = dict()
                for error in file_modification["errors"]:
                    if (error["source"] not in result["errors_origine"][type]):
                        result["errors_origine"][type][error["source"]] = 0
                    result["errors_origine"][type][error["source"]] += 1

    for result in results:
        compute_error_origines(result)

    def compute_errors_layer(injection_type):
        layers = dict()
        for result in results:
            if injection_type in result["errors_origine"]:
                errors = result["errors_origine"][injection_type]
            else:
                errors = []
            for error_label in errors_labels:
                if ( error_label not in layers):
                    layers[error_label] = []
                if ( error_label in errors ):
                    layers[error_label].append(errors[error_label])
                else:
                    layers[error_label].append(0)
        return layers

    layers = dict()
    for count in counts:
        layers[count] = compute_errors_layer(count)


    barWidth = 1. / (len(counts) + 1)
    # Set position of bar on X axis
    r = []
    r.append(np.arange(len(labels)))
    for i in range(1,len(counts)):
        r.append([x + barWidth for x in r[i-1]])


    def add_layers_to_the_graph(layers, position):
        sum = [0] * len(labels)
        for key, values in layers.items():
            plt.bar(position, values, width=barWidth, color=lables_colors[key], bottom=sum, edgecolor='white')
            sum = list(map( lambda x, y: x + y, sum, values))
        return sum
    # plt.bar(r2, naturalize_res, color='#f1c40f', width=barWidth, edgecolor='white', label='Naturalize')
    # plt.bar(r3, codebuff_res, color='#1abc9c', width=barWidth, edgecolor='white', label='Codebuff')
    i = 0
    sums = []
    for count, i in zip(counts, range(len(counts))):
        sums.append(add_layers_to_the_graph(layers[count], r[i]))


    # Add xticks on the middle of the group bars
    plt.xlabel('Number of errors (m={}) \n {}'.format(modifications, counts), fontweight='bold')
    plt.xticks([r + barWidth * (len(counts)-1) / 2 for r in range(len(results))], labels, rotation=45, fontsize=8)
    plt.subplots_adjust(top=0.80)

    plt.subplots_adjust(bottom=0.30)
    # Create legend & Show graphic
    patches = [ mpatches.Patch(color=c, label="{}".format(l.split(".")[-1])) for l, c in lables_colors.items()]
    plt.legend(handles = patches, loc='upper center', ncol=3, fancybox=True, bbox_to_anchor=(0.5, 1.4))

def load_results(dir):
    data = {}
    with open(os.path.join(dir, "results.json")) as f:
        data = json.load(f)
    return data

if __name__ == "__main__":
    fig_name = "figure"
    now = datetime.datetime.now()
    if ( len(sys.argv) > 2):
        type = sys.argv[1]
        save = False
        show = True
        i = 2
        while sys.argv[i].startswith("--"):
            if ( sys.argv[i] == "--save" ):
                save = True
            if ( sys.argv[i] == "--dontShow" ):
                show = False
            i+=1
        folders = sys.argv[i:]
        results = [ load_results(dir) for dir in folders ]
        if (type == "protocol1" or type == "1"):
            fig_name = "Experiment_injection_protocol1_{}".format(now.strftime("%Y%m%d_%H%M%S"))
            plot_errors_types(results, ("checkstyle_errors_count_ugly", "checkstyle_errors_count_naturalize", "checkstyle_errors_count_naturalize_snipper", "checkstyle_errors_count_codebuff", "checkstyle_errors_count_codebuff_snipper"))
            repair_tools = ("naturalize", "naturalize_snipper", "codebuff", "codebuff_snipper")
            union = lambda x, y: x|set(y);
            parse_error_name = lambda x: x.split(".")[-1];
            parse_error_names = lambda repair_tool, result: map(parse_error_name, result["checkstyle_errors_count_{}".format(repair_tool)].keys());
            get_error_names = lambda repair_tool, results: map(lambda result: parse_error_names(repair_tool, result), results)
            errors_types = {repair_tool: reduce(union, get_error_names(repair_tool, results), set()) for repair_tool in repair_tools}
            ugly_error_types = reduce(union, get_error_names("ugly", results), set())
            unique_error_types = {repair_tool: list(filter(lambda x: x not in ugly_error_types, errors)) for repair_tool, errors in errors_types.items()}
            print(unique_error_types)
        elif (type == "protocol2" or type == "2"):
            fig_name = "Experiment_injection_protocol2_{}".format(now.strftime("%Y%m%d_%H%M%S"))
            plot_errors_types(results, ("checkstyle_errors_count_ugly",))
        elif (type == "protocol3" or type == "3"):
            fig_name = "Experiment_injection_protocol3_{}".format(now.strftime("%Y%m%d_%H%M%S"))
            plot_errors_types_per_injection_type(results)
        elif (type == "protocol4" or type == "4"):
            plot_repaired_files(results)
        elif (type == "protocol5" or type == "5"):
            plot_diffs(results)
        elif (type == "protocol6" or type == "6"):
            protocol6(results, "naturalize_snipper")
        elif (type == "percentage_of_errors"):
            plot_percentage_of_errors(results)
        if show:
            try:
                plt.show()
            except UnicodeDecodeError:
                print("Bye mac os lover")
        if save:
            plt.savefig("../results/{}.pdf".format(fig_name), format='pdf')
